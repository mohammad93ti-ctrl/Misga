# Misga (fork)

**[English](#english)** | **[فارسی](#farsi)**

---

<a id="english"></a>

# Misga fork — dual-SIM, spam triage, reliable filters

A fork of [mirarr-app/Misga](https://github.com/mirarr-app/Misga) (upstream v1.1.0), an Android SMS app built for Iran's spam-heavy messaging environment. This fork focuses on dual-SIM usage, spam triage, and filter reliability. The filter engine, allowlist priority, Filter Studio, and Material 3 UI remain unchanged.

## How this fork differs from upstream

| Area | Upstream | This fork |
|---|---|---|
| Dual-SIM sending | Always sends via the system default SIM | In-field `SIM 1 / SIM 2` switch (chat and compose), per-conversation SIM memory, and a subtle `from/via SIM` caption under each message |
| Spam handling | Spam stays inline with a badge | `All / Hide spam / Spam only` filter chips (bulk-delete from `Spam only`); a silent in-app notice on new arrivals, no system notification |
| Copying text | Whole message only | Select and copy any span (long-press → dialog → select) |
| Rule edits | Affect new messages only | Re-evaluates all stored messages (manual Not-Spam decisions are preserved); duplicate rules are rejected with a hint |
| Rule order | Fixed, newest first | Same visible order, now backed by a persistent `sort_order` (DB v4) |
| Ghost threads | Search and compose can fabricate empty threads; one shortcode may split by prefix | No fabricated threads; phantom rows stay hidden; MMS-only chats explain themselves; shortcodes unify with and without the `+98` prefix |
| Tests | 47 unit tests | 50 unit tests |

## Install

Requires Android 8.0 (API 26) or newer. Set Misga as the **default SMS app** so it can intercept messages in real time.

## Build from source

Requires JDK 17 and the Android SDK:

```bash
./gradlew assembleDebug
```

The debug APK is written to `app/build/outputs/apk/debug/`.

## License

GNU General Public License v3.0 — same as upstream. See [LICENSE](LICENSE).

The original work is by [mirarr-app/Misga](https://github.com/mirarr-app/Misga); all fork modifications are likewise GPL-3.0. If you distribute the APK, the corresponding source must remain available.

---

<a id="farsi"></a>

<div dir="rtl" lang="fa">

# فورک میسگا — دوسیم‌کارته، مدیریت اسپم، فیلترهای قابل‌اعتماد

فورکی از [mirarr-app/Misga](https://github.com/mirarr-app/Misga) (نسخهٔ ۱٫۱٫۰)؛ برنامهٔ پیامک اندروید برای فضای پراسپم ایران. تمرکز این فورک روی کار با دو سیم‌کارت، مدیریت اسپم و قابل‌اعتماد بودن فیلترهاست. موتور فیلتر، اولویت Allowlist، استودیو فیلتر و رابط Material 3 بدون تغییر مانده‌اند.

## تفاوت این فورک با نسخهٔ اصلی

| بخش | نسخهٔ اصلی | این فورک |
|---|---|---|
| ارسال دوسیم‌کارته | همیشه با سیم پیش‌فرض سیستم | سوییچ `SIM 1 / SIM 2` داخل فیلد پیام (چت و پیام جدید)، یادآوری سیم هر مکالمه، و برچسب کوچک زیر هر پیام |
| مدیریت اسپم | فقط نشان داخل لیست | چیپ‌های All/Hide/Spam only (حذف دسته‌ای)، اطلاع‌رسانی داخل برنامه بدون نوتیفیکیشن سیستمی |
| کپی متن | فقط کل پیام | انتخاب و کپی هر بخش از متن |
| ویرایش قوانین | فقط روی پیام‌های جدید اثر می‌کند | بازبینی همهٔ پیام‌های ذخیره‌شده (تصمیم دستی Not-Spam حفظ می‌شود)؛ قانون تکراری با راهنما رد می‌شود |
| تردهای خالی | گاهی ترد خالی ساخته می‌شد | بدون ترد ساختگی؛ شرت‌کد با پیش‌شماره و بدون آن یکی حساب می‌شود |
| تست | ۴۷ تست واحد | ۵۰ تست واحد |

## نصب

اندروید ۸ به بالا لازم است. میسگا را **برنامهٔ پیش‌فرض پیامک** کنید.

## لایسنس

همان نسخهٔ اصلی: GNU GPL نسخهٔ ۳ ([LICENSE](LICENSE)). اثر اصلی از [mirarr-app/Misga](https://github.com/mirarr-app/Misga) است و تغییرات این فورک نیز GPL-3 است.

</div>
