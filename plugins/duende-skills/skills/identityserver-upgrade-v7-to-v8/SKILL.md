---
name: identityserver-upgrade-v7-to-v8
description: "Migrating Duende IdentityServer from v7.4 to v8.0: breaking changes, API replacements (ICache→HybridCache, IClock→TimeProvider), CancellationToken additions, EF migrations, and step-by-step upgrade guide."
---
# Upgrading IdentityServer v7 to v8

## When to Use This Skill

- Upgrading a Duende IdentityServer project from v7.4 to v8.0
- Fixing build errors after updating NuGet packages to v8
- Migrating custom stores/services to new v8 interfaces
- Running EF Core database migrations for v8 (SAML tables)
- Replacing deprecated APIs (ICache, IClock, IAuthorizationParametersMessageStore)

## Core Principles

- v8.0 requires **.NET 10** — update TFM before anything else
- All breaking changes are compile-time errors (no silent behavior changes)
- Migration is mechanical — find/replace patterns work for most changes
- Run EF migrations even if you don't use SAML (schema must match)
- **Always check the latest stable 8.x package version** on [NuGet](https://www.nuget.org/packages/Duende.IdentityServer) before upgrading — do not hardcode `8.0.1`; use whatever the latest stable (non-prerelease) 8.x version is at the time of the upgrade.

Docs: https://docs.duendesoftware.com/identityserver/upgrades/v7_4-to-v8_0/

## Step-by-Step Migration

### 1. Update Target Framework

```xml
<!-- ❌ Before -->
<TargetFramework>net8.0</TargetFramework>

<!-- ✅ After -->
<TargetFramework>net10.0</TargetFramework>
```

### 2. Update NuGet Packages

Check [NuGet](https://www.nuget.org/packages/Duende.IdentityServer) for the latest stable 8.x version. At time of writing, that is `8.0.1`, but use whatever is current:

```xml
<PackageReference Include="Duende.IdentityServer" Version="8.0.1" />
<PackageReference Include="Duende.IdentityServer.EntityFramework" Version="8.0.1" />
<!-- Update all Duende.* packages to the latest stable 8.x version -->
```

### 3. Run EF Database Migrations

Two migrations are required — one for the Configuration Store and one for the Operational Store:

```bash
# Configuration Store — adds 7 SAML-related tables
dotnet ef migrations add Update_DuendeIdentityServer_v8_0 \
    -c ConfigurationDbContext -o Migrations/ConfigurationDb
dotnet ef database update -c ConfigurationDbContext

# Operational Store — adds 3 SAML session tables
dotnet ef migrations add Update_DuendeIdentityServer_v8_0_Saml \
    -c PersistedGrantDbContext -o Migrations/PersistedGrantDb
dotnet ef database update -c PersistedGrantDbContext
```

Both are required even if you don't use SAML (schema must match).

### 4. Replace ICache<T> with HybridCache

```csharp
// ❌ Before (v7)
public class MyService
{
    private readonly ICache<MyData> _cache;
    public MyService(ICache<MyData> cache) => _cache = cache;

    public async Task<MyData> GetAsync(string key)
    {
        return await _cache.GetOrAddAsync(key,
            TimeSpan.FromMinutes(5),
            () => LoadFromDbAsync(key));
    }
}

// ✅ After (v8) — use Microsoft HybridCache
public class MyService
{
    private readonly HybridCache _cache;
    public MyService([FromKeyedServices("ConfigurationStoreCache")] HybridCache cache)
        => _cache = cache;

    public async Task<MyData> GetAsync(string key, CancellationToken ct)
    {
        return await _cache.GetOrCreateAsync(key,
            async token => await LoadFromDbAsync(key, token),
            new HybridCacheEntryOptions
            {
                Expiration = TimeSpan.FromMinutes(5)
            }, cancellationToken: ct);
    }
}
```

Key: use keyed service `"ConfigurationStoreCache"` (`ServiceProviderKeys.ConfigurationStoreCache`). `CachingOptions.CacheLockTimeout` is obsolete.

### 5. Replace IClock with TimeProvider

```csharp
// ❌ Before (v7)
public class MyService
{
    private readonly IClock _clock;
    public MyService(IClock clock) => _clock = clock;
    public DateTime Now => _clock.UtcNow.UtcDateTime;
}

// ✅ After (v8)
public class MyService
{
    private readonly TimeProvider _timeProvider;
    public MyService(TimeProvider timeProvider) => _timeProvider = timeProvider;
    public DateTime Now => _timeProvider.GetUtcNow().UtcDateTime;
}
```

Note: `GetUtcNow()` (method) replaces `UtcNow` (property).

### 6. Add CancellationToken to All Async Interfaces

All store and service interfaces now require `CancellationToken ct` as the last parameter:

```csharp
// ❌ Before (v7)
public Task<Client?> FindClientByIdAsync(string clientId)

// ✅ After (v8)
public Task<Client?> FindClientByIdAsync(string clientId, CancellationToken ct)
```

Affected interfaces include: `IClientStore`, `IResourceStore`, `IPersistedGrantStore`, `IDeviceFlowStore`, `ICorsPolicyService`, `IProfileService`, and all custom stores/services.

Also: `ICancellationTokenProvider` is removed entirely.

### 7. Add GetAllClientsAsync to IClientStore

```csharp
// ✅ New required method
public IAsyncEnumerable<Client> GetAllClientsAsync(CancellationToken ct)
```

Used by Financial-Grade Security features and conformance reports.

### 8. Update Refresh Token Service

```csharp
// ❌ Before (v7) — individual parameters
public Task<string> CreateRefreshTokenAsync(
    ClaimsPrincipal subject, Token accessToken, Client client)

// ✅ After (v8) — request objects
public Task<string> CreateRefreshTokenAsync(RefreshTokenCreationRequest request, CancellationToken ct)
public Task<string> UpdateRefreshTokenAsync(RefreshTokenUpdateRequest request, CancellationToken ct)
```

### 9. Remove IAuthorizationParametersMessageStore

```csharp
// ❌ Removed in v8 — use PAR (Pushed Authorization Requests) instead
services.AddTransient<IAuthorizationParametersMessageStore, MyStore>();

// ✅ PAR is the replacement for passing large authorization parameters
```

### 10. Fix Return Type Changes

Nine interfaces changed `IEnumerable<T>` → `IReadOnlyCollection<T>`:

```csharp
// ❌ Before
public Task<IEnumerable<ApiScope>> FindApiScopesByNameAsync(IEnumerable<string> scopeNames)

// ✅ After
public Task<IReadOnlyCollection<ApiScope>> FindApiScopesByNameAsync(
    IEnumerable<string> scopeNames, CancellationToken ct)
```

### 11. Fix DPoP Type Names

```csharp
// ❌ Typo in v7
DPoPProofValidatonContext  → DPoPProofValidationContext
DPoPProofValidatonResult   → DPoPProofValidationResult
```

### 12. Update Licensing Code

```csharp
// ❌ Before (v7)
var license = IdentityServerLicense.Current;
var edition = summary.LicenseEdition;

// ✅ After (v8)
var info = LicenseInformation.Current;  // from Duende.IdentityServer.Licensing
var skus = summary.EntitledSkus;        // collection replaces single edition
```

### 13. Update EF Identity Provider Store

```csharp
// ❌ Before (v7)
public IdentityProviderStore(IServiceProvider sp, ConfigurationDbContext ctx)

// ✅ After (v8) — new required parameter
public IdentityProviderStore(
    IServiceProvider sp, ConfigurationDbContext ctx, IIdentityProviderFactory factory)
```

### 14. Rename AuthorizationError → InteractionError

```csharp
// ❌ Before (v7)
if (result.Error == AuthorizationError.LoginRequired) { }

// ✅ After (v8)
if (result.Error == InteractionError.LoginRequired) { }
```

Values remain the same: `AccessDenied`, `LoginRequired`, `InteractionRequired`.

### 15. Rename DenyAuthorizationAsync → DenyAuthenticationAsync

```csharp
// ❌ Before (v7)
await _interaction.DenyAuthorizationAsync(context, AuthorizationError.AccessDenied);

// ✅ After (v8) — now accepts IAuthenticationContext (protocol-agnostic for OIDC/SAML)
await _interaction.DenyAuthenticationAsync(context, InteractionError.AccessDenied);
```

### 16. Rename ProfileDataRequestContext.Client → .Application

```csharp
// ❌ Before (v7)
var client = context.Client;

// ✅ After (v8)
var client = context.Application;
```

### 17. Update ITokenValidator.ValidateAccessTokenAsync

```csharp
// ❌ Before (v7)
await _validator.ValidateAccessTokenAsync(token);

// ✅ After (v8) — new expectedScope parameter
await _validator.ValidateAccessTokenAsync(token, expectedScope: null, ct);
```

### 18. Relocate PreviewFeatureOptions

`PreviewFeatureOptions` and `IdentityServerOptions.Preview` are removed. Options relocated:

```csharp
// ❌ Before (v7)
options.Preview.EnableDiscoveryDocumentCache = true;
options.Preview.DiscoveryDocumentCacheDuration = TimeSpan.FromMinutes(10);
options.Preview.StrictClientAssertionAudienceValidation = true;

// ✅ After (v8)
options.Discovery.EnableDiscoveryDocumentCache = true;
options.Discovery.DiscoveryDocumentCacheDuration = TimeSpan.FromMinutes(10);
options.StrictClientAssertionAudienceValidation = true;  // default changed to false!
```

## Other Notable Changes

- **NRT enabled**: All assemblies use nullable reference types. Fix nullable warnings.
- **HTTP 303**: POST endpoint redirects now unconditionally use 303 (FAPI 2.0 compliance).
- **`PersistedGrantFilter.ClientIds`/`Types`**: Now non-nullable with empty collection defaults. Replace null checks with `.Count > 0`.
- **IUserSession**: Three new SAML session methods added (implement as no-op if not using SAML):
  - `AddSamlSessionAsync`, `GetSamlSessionListAsync`, `RemoveSamlSessionAsync`
- **Log levels**: Secret validation failures changed from Error to Debug — update alerting to watch for Warning-level entries at endpoint level instead.
- **Device flow consent**: "Remember My Decision" no longer offered — `RememberConsent` always `false` during device flow (RFC 8628 security).
- **License key from IConfiguration**: IdentityServer now reads license key automatically from `Duende:IdentityServer:LicenseKey` or `Duende:LicenseKey` in configuration.
- **`DPoPExtensions` → `DPoPServiceCollectionExtensions`**: Class renamed in JwtBearer package.
- **Token cleanup performance**: When no `IOperationalStoreNotification` registered, uses single `ExecuteDeleteAsync` call (automatic improvement, no action needed).
- **Orphaned grants revoked on session overwrite**: When server-side sessions enabled and session cookie reused by different user, previous user's grants are automatically revoked.

## Migration Checklist

1. ☐ Update TFM to `net10.0`
2. ☐ Update all Duende.* packages to latest stable 8.x (check [NuGet](https://www.nuget.org/packages/Duende.IdentityServer))
3. ☐ Run EF migrations (both `ConfigurationDbContext` and `PersistedGrantDbContext`)
4. ☐ Replace `ICache<T>` → keyed `HybridCache`
5. ☐ Replace `IClock` → `TimeProvider`
6. ☐ Add `CancellationToken` to all async store/service methods
7. ☐ Remove `ICancellationTokenProvider` references
8. ☐ Add `GetAllClientsAsync` to custom `IClientStore` (returns `IAsyncEnumerable<Client>`)
9. ☐ Update `IRefreshTokenService` implementations (request objects)
10. ☐ Remove `IAuthorizationParametersMessageStore` (use PAR)
11. ☐ Fix `IEnumerable<T>` → `IReadOnlyCollection<T>` return types
12. ☐ Fix DPoP type name typos
13. ☐ Update licensing references (`IdentityServerLicense` → `LicenseInformation`)
14. ☐ Rename `AuthorizationError` → `InteractionError`
15. ☐ Rename `DenyAuthorizationAsync` → `DenyAuthenticationAsync`
16. ☐ Rename `ProfileDataRequestContext.Client` → `.Application`
17. ☐ Update `ITokenValidator.ValidateAccessTokenAsync` calls (add `expectedScope` param)
18. ☐ Relocate `PreviewFeatureOptions` settings
19. ☐ Fix nullable reference type warnings
20. ☐ Test build and run

## Common Pitfalls

1. **Forgetting EF migration**: Even without SAML, the schema must be updated or EF will throw at runtime.
2. **HybridCache keyed service**: Must use `[FromKeyedServices("ConfigurationStoreCache")]` — plain `HybridCache` injection gets a different instance.
3. **CancellationToken propagation**: Don't pass `CancellationToken.None` everywhere — propagate from the method parameter for proper request cancellation.
4. **GetAllClientsAsync performance**: Return all clients from your store; used rarely but must be implemented.
5. **PAR migration**: If you used `IAuthorizationParametersMessageStore` for large auth requests, switch clients to use PAR (`require_pushed_authorization_requests`).

## Related Skills

- `identityserver-configuration` — IdentityServer host configuration and options
- `identityserver-stores` — Store implementation patterns (affected by CancellationToken changes)
- `identityserver-saml` — SAML 2.0 support (new in v8, requires EF migration)
- `identityserver-usermanagement` — User Management (new in v8)