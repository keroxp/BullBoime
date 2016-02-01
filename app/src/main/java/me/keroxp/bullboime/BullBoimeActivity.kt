package me.keroxp.bullboime

import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceActivity

class BullBoimeActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        title = getString(R.string.setting_name)
    }
}