# selenium-java-cucumber

UI + API test framework built on **Selenium 4 + Java 17 + Cucumber + TestNG + Rest-Assured**, with Maven, multi-browser support, parallel execution and GitHub Actions CI.

UI scenarios run against [saucedemo.com](https://www.saucedemo.com); API tests run against [reqres.in](https://reqres.in). Both are public, so the suite is reproducible end-to-end.

## Why this exists

This is the framework shape I've built and rebuilt for enterprise teams — JVM-based, BDD on top of POM, and an API layer that runs in the same suite so a single CI job tells you whether the platform is healthy at both surfaces.

- **Cucumber + TestNG** instead of Cucumber + JUnit — TestNG's `@DataProvider(parallel = true)` gives clean scenario-level parallelism without extra plugins
- **Thread-local WebDriver** so parallel scenarios never share state
- **Selenium Manager** (built into Selenium 4) handles browser binaries — no `webdrivermanager` dependency
- **Rest-Assured** API tests live alongside UI tests, runnable independently via a Maven profile
- **Two suite XMLs** so CI can run `regression` (UI + API) or `api-only` against any environment

## Stack

| Layer | Choice |
|---|---|
| Browser driver | Selenium `4.25` |
| Test runner | TestNG `7.10` (via `cucumber-testng`) |
| BDD | Cucumber-JVM `7.20` |
| API | Rest-Assured `5.5` |
| Build | Maven |
| Language | Java 17 |
| Assertions | AssertJ |

## Repo layout

```
.
├── pom.xml
├── src/test/java/com/fahimalam/qa/
│   ├── pages/                Page Objects (POM)
│   ├── steps/                Cucumber step definitions
│   ├── support/              DriverFactory (thread-local), Hooks
│   ├── runners/              CucumberRunner (parallel scenarios)
│   └── api/                  Rest-Assured TestNG tests
├── src/test/resources/
│   ├── features/             *.feature files
│   ├── suites/regression.xml UI + API
│   └── suites/api.xml        API-only
└── .github/workflows/ci.yml  Chrome + Firefox matrix
```

## Run it

```bash
# Default profile — Chrome, headless, full suite (UI + API)
mvn test

# Firefox
mvn test -Pfirefox

# API-only (fast smoke for any environment)
mvn test -Papi-only

# Local debugging with a visible browser
mvn test -Dheadless=false

# Skip cart scenarios (what CI does — see note below)
mvn test -Dcucumber.filter.tags="not @cart-ui"
```

### What CI runs vs. what runs locally

CI runs the API smoke plus the login UI scenarios on every push. The cart
scenarios are tagged `@cart-ui` and excluded from CI: saucedemo.com's
add-to-cart button doesn't reliably transition state in headless Chrome under
parallel load — the click fires but the post-click DOM update is racy. Those
scenarios are kept local-only so the badge stays honest. Run `mvn test`
locally with a real browser to exercise the whole suite, including cart.

The Cucumber HTML report lands in `target/cucumber-report.html`. Failed scenarios attach a full-page screenshot to the report automatically.

## Design notes

**Why thread-local WebDriver?** Cucumber + TestNG runs scenarios in parallel from a shared `@DataProvider`. A static `WebDriver` would alias across threads. `ThreadLocal<WebDriver>` gives each thread its own driver, and `DriverFactory.quit()` in the `@After` hook prevents leaks between scenarios.

**Why separate suite XMLs?** UI tests need browsers; API tests don't. Keeping the suites separate lets a deploy gate run the API checks in 15 seconds without spinning up Chrome.

**No PageFactory.** Selenium's `@FindBy` is fine but couples discovery time to field initialization. Plain `By` constants plus `WebDriverWait` are easier to reason about and cheaper to mock.

## What I'd add next

- Allure reporter + history server for trend analysis
- Jenkins `Jenkinsfile` alongside the GitHub Actions matrix
- Selenium Grid / BrowserStack integration via a `RemoteWebDriver` switch
- Cross-suite shared fixtures (auth tokens) loaded once per JVM

---

Built by [Fahim Alam](https://github.com/fahimalamwork) — Senior QA / SDET, NYC.
