# Misga (fork)

**[English](#english)** | **[فارسی](#farsi)**

---

<a id="english"></a>

# Misga fork — dual-SIM, smarter spam, retroactive rules

A fork of [mirarr-app/Misga](https://github.com/mirarr-app/Misga) — an Android SMS app for Iran's spam-heavy SMS situation — with extra features on top.

## What's new in this fork

- **Dual-SIM sending** — a `SIM 1 / SIM 2` switch inside the message field (Chat + New Message), per-conversation memory, and a tiny `from/via SIM` label under every message.
- **Spam controls in place** — `All / Hide spam / Spam only` chips above the inbox (bulk-delete spams from `Spam only`), plus a quiet in-app note when new spam arrives (no system notification).
- **Rules apply retroactively** — changing a rule re-evaluates all stored messages; messages you manually un-spammed stay untouched. Duplicate rules are rejected with a hint.
- **Ghost-thread fixes** — search no longer creates empty threads, no empty thread on unsent compose, phantom threads hidden, MMS-only chats explain themselves.
- **Shortcode identity** — `100065`, `+98100065` and `0100065` are treated as one sender (same for landlines).

## Install

Android 8.0 (API 26)+. Set Misga as the **default SMS app** so it can intercept messages.

## Build from source

JDK 17 + Android SDK:

```bash
./gradlew assembleDebug
```

APK goes to `app/build/outputs/apk/debug/`.

## License

GNU GPL v3 — same as the original. See [LICENSE](LICENSE).

Original work by [mirarr-app/Misga](https://github.com/mirarr-app/Misga); fork modifications are also GPL-3.0. If you distribute the APK, keep the source available.

---

<a id="farsi"></a>

<div dir="rtl" lang="fa">

# فورک میسگا — دوسیم‌کارته، اسپم هوشمندتر، قوانین عطف‌به‌ماسبق

فورکی از [mirarr-app/Misga](https://github.com/mirarr-app/Misga) — برنامه پیامک اندروید برای وضعیت پراسپم ایران — با چند قابلیت اضافه.

## چیزهای جدید این فورک

- **ارسال دوسیم‌کارته** — سوییچ `SIM 1 / SIM 2` داخل فیلد پیام (چت و پیام جدید)، یادآوری سیم هر مکالمه، و لیبل کوچک `from/via SIM` زیر هر پیام.
- **کنترل اسپم سر جاش** — چیپ‌های `All / Hide spam / Spam only` بالای لیست (حذف دسته‌ای از حالت Spam only) + اطلاع داخل برنامه موقع اسپم جدید (بدون نوتیف سیستمی).
- **اعمال قوانین به گذشته** — با تغییر قانون، همه پیام‌های ذخیره‌شده دوباره ارزیابی می‌شن؛ پیامی که دستی Not-Spam کردی دست نمی‌خوره. قانون تکراری با پیام رد می‌شه.
- **رفع تردهای گوست** — سرچ دیگه ترد خالی نمی‌سازه، چت خالی برای MMS توضیح می‌ده.
- **یکپارچگی شرت‌کد** — `100065` و `+98100065` یه فرستنده حساب می‌شن.

## نصب

اندروید ۸ به بالا. میسگا رو **برنامه پیش‌فرض پیامک** کن.

## لایسنس

مثل نسخه اصلی: GNU GPL نسخه ۳ ([LICENSE](LICENSE)). اثر اصلی از [mirarr-app/Misga](https://github.com/mirarr-app/Misga)؛ تغییرات فورک هم GPL-3 است.

</div>
