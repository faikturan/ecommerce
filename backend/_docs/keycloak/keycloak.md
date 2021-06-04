### Admin CLI

Customer service will interact with Keycloak via `admin-cli` client, therefore:
1. Create role `ROLE_ADMIN_CLI` (see file `ROLE_ADMIN_CLI.png`)
2. Create user `admin-cli` with password `12345678` (config in YAML file) with role `ROLE_ADMIN_CLI`

---

### Gateway client

#### Change token timeout

Change at Additional settings block

#### Add additional attributes

See `gateway-client.png` file