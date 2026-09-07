# Graph Report - misga  (2026-09-07)

## Corpus Check
- Corpus is ~45,359 words - fits in a single context window. You may not need a graph.

## Summary
- 506 nodes · 1026 edges · 27 communities (17 shown, 7 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 7 edges (avg confidence: 0.86)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- Conversation UI Components
- Database Filter Cache
- Repository Filter Engine
- Inbox Conversations ViewModel
- Filter Studio UI
- Chat ViewModel
- SMS Send Tracking
- Multipart SMS Assembler
- Notifications Handling
- MainActivity Navigation
- Incoming SMS Policy
- Predefined Rules Tests
- Headless Send Service
- SMS Receiver
- Test Lab Simulator
- Phone Number Keys
- CI CD Pipelines
- MMS Receiver
- Gradle Wrapper
- Default SMS Helper
- Allowlist Blocklist Concepts
- Release Version Checks
- Default SMS Requirement
- Filter Studio Concepts

## God Nodes (most connected - your core abstractions)
1. `MisgaDatabaseHelper` - 48 edges
2. `SmsRepository` - 44 edges
3. `ConversationsViewModel` - 28 edges
4. `FilterRule` - 25 edges
5. `ChatViewModel` - 24 edges
6. `FilterAction` - 22 edges
7. `FilterStudioViewModel` - 22 edges
8. `SenderPreference` - 16 edges
9. `ConversationThread` - 16 edges
10. `PhoneNumberKeys` - 15 edges

## Surprising Connections (you probably didn't know these)
- `Debug APK Build via assembleDebug` --semantically_similar_to--> `Release Signed Release APK Build`  [INFERRED] [semantically similar]
  .github/workflows/ci.yml → .github/workflows/release.yml
- `Build from Source with assembleDebug` --references--> `Debug APK Build via assembleDebug`  [EXTRACTED]
  README.md → .github/workflows/ci.yml
- `Pre-release Pipeline` --references--> `Universal Signed APK Artifact`  [EXTRACTED]
  .github/workflows/pre-release.yml → README.md
- `Release Pipeline` --references--> `Universal Signed APK Artifact`  [EXTRACTED]
  .github/workflows/release.yml → README.md
- `MainNavigation()` --calls--> `ComposeNav`  [EXTRACTED]
  app/src/main/java/com/miss/ga/Navigation.kt → app/src/main/java/com/miss/ga/NavigationKeys.kt

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Incoming SMS filtering evaluation order** — readme_allowlist, readme_blocklist, readme_sender_settings [EXTRACTED 1.00]
- **Signed universal APK release delivery** — github_workflows_pre_release_signed_apk_build, github_workflows_release_signed_apk_build, readme_universal_apk_artifact [EXTRACTED 1.00]

## Communities (27 total, 7 thin omitted)

### Community 0 - "Conversation UI Components"
Cohesion: 0.07
Nodes (46): AnnotatedString, SmsMessage, ContactItem, MainNavigation(), ChatNav, getAvatarGradient(), ConversationAvatar(), Modifier (+38 more)

### Community 1 - "Database Filter Cache"
Cohesion: 0.08
Nodes (14): FilterRuleIndices, FilterRuleOverride, ContentValues, Context, MisgaDatabaseHelper, SenderPreferenceIndices, SpamMessageMeta, SpamMetaWrite (+6 more)

### Community 2 - "Repository Filter Engine"
Cohesion: 0.08
Nodes (14): CachedLookupMap, ContentValues, SearchHit, SendSmsResult, SmsRepository, ThreadProviderRow, CompiledFilterRule, FilterResult (+6 more)

### Community 3 - "Inbox Conversations ViewModel"
Cohesion: 0.08
Nodes (11): ConversationThread, SearchMessageResult, ConversationsUiState, ConversationsViewModel, ContentObserver, AndroidViewModel, ContentObserver, Job (+3 more)

### Community 4 - "Filter Studio UI"
Cohesion: 0.10
Nodes (23): RuleListType, ALLOWLIST, BLOCKLIST, FilterRule, AddGlobalRuleDialog(), AllowlistTab(), BlocklistTab(), EditRuleDialog() (+15 more)

### Community 5 - "Chat ViewModel"
Cohesion: 0.08
Nodes (19): FilterAction, NORMAL, SILENT, SPAM, RuleCategory, CUSTOM, CUSTOM_ALLOWLIST, FINANCIAL_SCAM (+11 more)

### Community 6 - "SMS Send Tracking"
Cohesion: 0.12
Nodes (8): BroadcastReceiver, Context, Intent, SmsSendTracker, SmsSentReceiver, State, SmsSendTrackerTest, CompletableDeferred

### Community 7 - "Multipart SMS Assembler"
Cohesion: 0.21
Nodes (5): AssembledIncomingMessages, IncomingMultipartAssembler, IncomingSmsPart, IncomingMultipartAssemblerTest, T

### Community 8 - "Notifications Handling"
Cohesion: 0.16
Nodes (8): Context, NotificationHelper, BroadcastReceiver, Context, Intent, NotificationActionReceiver, NotificationActions, NotificationCompat

### Community 9 - "MainActivity Navigation"
Cohesion: 0.17
Nodes (11): Bundle, Intent, Uri, MainActivity, ComposeNav, ConversationsNav, FilterStudioNav, TestLabNav (+3 more)

### Community 12 - "Headless Send Service"
Cohesion: 0.27
Nodes (6): HeadlessSmsSendService, Bundle, Intent, Uri, IBinder, Service

### Community 13 - "SMS Receiver"
Cohesion: 0.26
Nodes (5): AndroidSmsMessage, BroadcastReceiver, Context, Intent, SmsReceiver

### Community 14 - "Test Lab Simulator"
Cohesion: 0.27
Nodes (7): FakeSmsScenario, FakeSmsSimulator, Context, SimulationResult, Modifier, ScenarioCard(), TestLabScreen()

### Community 16 - "CI CD Pipelines"
Cohesion: 0.29
Nodes (8): CI Pipeline, Debug APK Build via assembleDebug, Pre-release Pipeline, Pre-release Signed Release APK Build, Release Pipeline, Release Signed Release APK Build, Build from Source with assembleDebug, Universal Signed APK Artifact

### Community 17 - "MMS Receiver"
Cohesion: 0.53
Nodes (4): BroadcastReceiver, Context, Intent, MmsReceiver

### Community 18 - "Gradle Wrapper"
Cohesion: 0.70
Nodes (4): gradlew script, die(), save(), warn()

### Community 20 - "Allowlist Blocklist Concepts"
Cohesion: 0.67
Nodes (3): Allowlist, Blocklist, Sender Mute and Block Settings

## Knowledge Gaps
- **25 isolated node(s):** `NORMAL`, `SILENT`, `SPAM`, `ALLOWLIST`, `BLOCKLIST` (+20 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 103 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SmsRepository` connect `Repository Filter Engine` to `Conversation UI Components`, `Database Filter Cache`, `Inbox Conversations ViewModel`, `Chat ViewModel`, `Notifications Handling`, `Headless Send Service`, `SMS Receiver`, `Test Lab Simulator`?**
  _High betweenness centrality (0.240) - this node is a cross-community bridge._
- **Why does `MisgaDatabaseHelper` connect `Database Filter Cache` to `Repository Filter Engine`, `Inbox Conversations ViewModel`, `Filter Studio UI`, `Chat ViewModel`, `SMS Receiver`, `Test Lab Simulator`?**
  _High betweenness centrality (0.137) - this node is a cross-community bridge._
- **Why does `FilterAction` connect `Chat ViewModel` to `Conversation UI Components`, `Database Filter Cache`, `Repository Filter Engine`, `Filter Studio UI`, `Notifications Handling`, `Predefined Rules Tests`, `SMS Receiver`, `Test Lab Simulator`?**
  _High betweenness centrality (0.133) - this node is a cross-community bridge._
- **What connects `NORMAL`, `SILENT`, `SPAM` to the rest of the system?**
  _25 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Conversation UI Components` be split into smaller, more focused modules?**
  _Cohesion score 0.0744047619047619 - nodes in this community are weakly interconnected._
- **Should `Database Filter Cache` be split into smaller, more focused modules?**
  _Cohesion score 0.08417508417508418 - nodes in this community are weakly interconnected._
- **Should `Repository Filter Engine` be split into smaller, more focused modules?**
  _Cohesion score 0.07801418439716312 - nodes in this community are weakly interconnected._