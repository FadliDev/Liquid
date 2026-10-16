package com.sally.liquidclient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Locale;

public final class MainActivity extends Activity {
    private static final int REQUEST_IMPORT_APK = 4101;

    private final int background = Color.rgb(13, 15, 19);
    private final int surface = Color.rgb(23, 26, 32);
    private final int surfaceRaised = Color.rgb(31, 35, 43);
    private final int accent = Color.rgb(155, 255, 93);
    private final int textPrimary = Color.rgb(244, 246, 248);
    private final int textSecondary = Color.rgb(155, 163, 175);

    private TextView apkStatus;
    private TextView storageStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(background);
        getWindow().setNavigationBarColor(background);
        getWindow().getDecorView().setSystemUiVisibility(0);
        setContentView(createScreen());
        refreshStatus();
    }

    private View createScreen() {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(background);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(22), dp(24), dp(22), dp(28));
        scroll.addView(page);

        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        TextView logo = text("LC", 18, accent, true);
        logo.setGravity(Gravity.CENTER);
        logo.setBackground(roundRect(accent, dp(14)));
        logo.setTextColor(Color.rgb(15, 18, 13));
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(dp(48), dp(48));
        top.addView(logo, logoParams);

        LinearLayout brand = new LinearLayout(this);
        brand.setOrientation(LinearLayout.VERTICAL);
        brand.setPadding(dp(12), 0, 0, 0);
        brand.addView(text("LIQUID CLIENT", 16, textPrimary, true));
        brand.addView(text("BEDROCK LAUNCHER", 10, textSecondary, true));
        top.addView(brand, new LinearLayout.LayoutParams(0, -2, 1));

        TextView version = text("v0.1.0", 11, textSecondary, true);
        version.setPadding(dp(10), dp(7), dp(10), dp(7));
        version.setBackground(roundRect(surfaceRaised, dp(20)));
        top.addView(version);
        page.addView(top);

        TextView eyebrow = text("YOUR GAME, YOUR SETUP.", 11, accent, true);
        eyebrow.setPadding(0, dp(34), 0, dp(9));
        page.addView(eyebrow);

        page.addView(text("One launcher.", 32, textPrimary, true));
        page.addView(text("Your Minecraft.", 32, textPrimary, true));

        TextView intro = text(
                "A clean home for your Bedrock setup. Import your own official game package to get started.",
                14, textSecondary, false);
        intro.setLineSpacing(dp(3), 1f);
        LinearLayout.LayoutParams introParams = new LinearLayout.LayoutParams(-1, -2);
        introParams.topMargin = dp(12);
        introParams.bottomMargin = dp(24);
        page.addView(intro, introParams);

        LinearLayout gameCard = card(page);
        TextView gameLabel = text("GAME INSTALLATION", 10, textSecondary, true);
        gameCard.addView(gameLabel);

        apkStatus = text("No APK imported", 20, textPrimary, true);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(-1, -2);
        statusParams.topMargin = dp(13);
        gameCard.addView(apkStatus, statusParams);

        TextView gameHint = text(
                "Stage 1 supports selecting and storing an APK privately. Game launching and version isolation will be added in later stages.",
                12, textSecondary, false);
        gameHint.setLineSpacing(dp(2), 1f);
        LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(-1, -2);
        hintParams.topMargin = dp(8);
        gameCard.addView(gameHint, hintParams);

        Button importButton = button("IMPORT OFFICIAL APK", true);
        LinearLayout.LayoutParams importParams = new LinearLayout.LayoutParams(-1, dp(50));
        importParams.topMargin = dp(20);
        gameCard.addView(importButton, importParams);
        importButton.setOnClickListener(v -> openApkPicker());

        LinearLayout toolsCard = card(page);
        toolsCard.addView(text("LAUNCHER MODULES", 10, textSecondary, true));
        addFeatureRow(toolsCard, "01", "Version manager", "Planned for the next stage");
        addFeatureRow(toolsCard, "02", "Native mod support", "SDK and loader integration");
        addFeatureRow(toolsCard, "03", "Worlds & resource packs", "Local content management");

        LinearLayout infoCard = card(page);
        infoCard.addView(text("DEVICE STORAGE", 10, textSecondary, true));
        storageStatus = text("Checking app storage…", 14, textPrimary, true);
        LinearLayout.LayoutParams storageParams = new LinearLayout.LayoutParams(-1, -2);
        storageParams.topMargin = dp(12);
        infoCard.addView(storageStatus, storageParams);

        TextView footer = text("LIQUID CLIENT  •  BUILT FROM SCRATCH", 10, textSecondary, true);
        footer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(-1, -2);
        footerParams.topMargin = dp(24);
        page.addView(footer, footerParams);

        return scroll;
    }

    private LinearLayout card(LinearLayout parent) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(18), dp(18), dp(18), dp(18));
        box.setBackground(roundRect(surface, dp(20)));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.bottomMargin = dp(14);
        parent.addView(box, params);
        return box;
    }

    private void addFeatureRow(LinearLayout parent, String number, String title, String subtitle) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(-1, -2);
        rowParams.topMargin = dp(17);
        parent.addView(row, rowParams);

        TextView num = text(number, 11, accent, true);
        num.setGravity(Gravity.CENTER);
        num.setBackground(roundRect(surfaceRaised, dp(10)));
        row.addView(num, new LinearLayout.LayoutParams(dp(38), dp(38)));

        LinearLayout labels = new LinearLayout(this);
        labels.setOrientation(LinearLayout.VERTICAL);
        labels.setPadding(dp(12), 0, 0, 0);
        labels.addView(text(title, 14, textPrimary, true));
        TextView sub = text(subtitle, 11, textSecondary, false);
        LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(-1, -2);
        subParams.topMargin = dp(3);
        labels.addView(sub, subParams);
        row.addView(labels, new LinearLayout.LayoutParams(0, -2, 1));
    }

    private Button button(String label, boolean primary) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextSize(12);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setAllCaps(false);
        b.setTextColor(primary ? Color.rgb(14, 17, 12) : textPrimary);
        b.setBackground(roundRect(primary ? accent : surfaceRaised, dp(14)));
        b.setPadding(dp(12), 0, dp(12), 0);
        return b;
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        if (bold) view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return view;
    }

    private android.graphics.drawable.GradientDrawable roundRect(int color, int radius) {
        android.graphics.drawable.GradientDrawable drawable = new android.graphics.drawable.GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    private void openApkPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.android.package-archive");
        try {
            startActivityForResult(intent, REQUEST_IMPORT_APK);
        } catch (Exception e) {
            Toast.makeText(this, "File picker is not available on this device.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_IMPORT_APK || resultCode != RESULT_OK || data == null || data.getData() == null) {
            return;
        }

        Uri uri = data.getData();
        try {
            String displayName = queryDisplayName(uri);
            if (displayName == null || !displayName.toLowerCase(Locale.ROOT).endsWith(".apk")) {
                Toast.makeText(this, "Please select an APK file.", Toast.LENGTH_LONG).show();
                return;
            }

            File destination = new File(getFilesDir(), "imports");
            if (!destination.exists() && !destination.mkdirs()) {
                throw new IllegalStateException("Cannot create import directory");
            }

            File output = new File(destination, "minecraft-import.apk");
            try (InputStream input = getContentResolver().openInputStream(uri);
                 FileOutputStream stream = new FileOutputStream(output)) {
                if (input == null) throw new IllegalStateException("Cannot read selected file");
                byte[] buffer = new byte[64 * 1024];
                int count;
                while ((count = input.read(buffer)) != -1) stream.write(buffer, 0, count);
            }

            getSharedPreferences("liquid_client", MODE_PRIVATE)
                    .edit().putString("imported_apk_name", displayName).apply();
            refreshStatus();
            Toast.makeText(this, "APK copied into Liquid Client storage.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Import failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String queryDisplayName(Uri uri) {
        android.database.Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex("_display_name");
                if (index >= 0) return cursor.getString(index);
            }
        } catch (Exception ignored) {
        } finally {
            if (cursor != null) cursor.close();
        }
        String path = uri.getLastPathSegment();
        return path == null ? null : path;
    }

    private void refreshStatus() {
        if (apkStatus != null) {
            String name = getSharedPreferences("liquid_client", MODE_PRIVATE)
                    .getString("imported_apk_name", null);
            apkStatus.setText(name == null ? "No APK imported" : "APK imported");
        }
        if (storageStatus != null) {
            File imports = new File(getFilesDir(), "imports");
            long bytes = 0L;
            if (imports.exists()) {
                File[] files = imports.listFiles();
                if (files != null) for (File file : files) bytes += file.length();
            }
            storageStatus.setText(String.format(Locale.getDefault(),
                    "App-private import storage · %.1f MB used", bytes / (1024f * 1024f)));
        }
    }
}
