# Misga project rules (project-local, only for this repo)

# Mandatory workflow

- BEFORE any code write/edit: load `karpathy-guidelines` skill and follow it (assumptions first, minimal code, surgical diff, verifiable success criteria).
- BEFORE editing God Nodes or bridge nodes (`MisgaDatabaseHelper`, `SmsRepository`, `ConversationsViewModel`, `FilterRule`, `ChatViewModel`, `FilterAction`): check the module map first (regenerate with graphify if stale), inspect community + blast radius. Never touch them silently.
- AFTER any Kotlin/Compose edit: load and apply the matching review skill(s): `kotlin-quality` (always for .kt), `compose-ui` (for Compose UI), `architecture` (for ViewModel/Repository/DI/layers), `performance` (for startup/recomposition/leaks), `security-audit` (for receivers, SMS data, storage, permissions), `play-store` (before any release).
- No "should work": every fix/feature must end with a real verification (build/test) per karpathy Goal-Driven Execution.
