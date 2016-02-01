package me.keroxp.bullboime

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodSubtype
import tv.loilo.LoiLog

class BullBoime : InputMethodService() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onCreateInputView(): View? {
        val view = layoutInflater.inflate(R.layout.layout_keyboard_view,null)
        return view
    }

    override fun onCreateCandidatesView(): View? {
        return super.onCreateCandidatesView()
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
    }

    override fun onCurrentInputMethodSubtypeChanged(newSubtype: InputMethodSubtype?) {
        super.onCurrentInputMethodSubtypeChanged(newSubtype)
    }

    override fun onFinishInput() {
        super.onFinishInput()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}