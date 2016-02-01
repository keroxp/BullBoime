package tv.loilo

import android.util.Log

object LoiLog {

    //これはgetCallerInfoが呼び出される、呼び出し元までのスタックの深さを指定します。getCallerInfoを呼び出す位置を変更したら、ここも見直す必要があります。
    private val CALL_STACK_INDEX = 2
    //NOTE よくあるサンプルとかだと4000文字になっているけど、4000文字だと途中で切れちゃうことがあった。もしかするとByte長なのかもしれない。暫定対処として半分にしておく。
    private val MAX_LOG_LENGTH = 2000
    private var mLevel: Int = 0

    private fun isEnabled(level: Int): Boolean {
        return mLevel <= level
    }

    fun setLevel(level: Int) {
        mLevel = level
    }

    private //new Throwable()はきっと処理負荷が高い。と思う。リリース時には呼ばないほうが安全な気がしています。
    val callerInfo: StackTraceElement
        get() {
            val stackTrace = Throwable().stackTrace
            if (stackTrace.size <= CALL_STACK_INDEX) {
                throw IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?")
            }
            return stackTrace[CALL_STACK_INDEX]
        }

    private fun createTag(callerInfo: StackTraceElement): String {
        val fullClassName = callerInfo.className
        val className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1)
        val dollarIndex = className.indexOf('$')
        val tag = if (dollarIndex < 0) className else className.substring(0, dollarIndex)
        if (tag.length <= 0) {
            return "Unknown"
        }

        //タグ名は23文字以内にしないといけないらしい。手元の端末では問題なかったけど、念のため短くしようと思ったが、ドキュメント見てもThrows宣言をしているのがLog.isLoggableメソッドだけだったので思いとどまった。
        //        if (tag.length() > 23) {
        //            final String part = tag.substring(0, 20);
        //            return part + "...";
        //        }
        return tag
    }

    private fun createFileLink(callerInfo: StackTraceElement): String {
        return "        at " + callerInfo
    }

    private fun breakText(text: String, start: Int, maxLength: Int): Int {
        return Math.min(maxLength, text.length - start)
        //        final String sub = text.substring(start, start + count);
        //        final int lineBreak = sub.indexOf('\n');
        //        if (lineBreak < 0) {
        //            return count;
        //        }
        //        return Math.min(lineBreak + 1, count);
    }

    private fun println(priority: Int, tag: String, msg: String) {
        if (priority == Log.ASSERT) {
            Log.wtf(tag, msg)
        } else {
            Log.println(priority, tag, msg)
        }
    }

    private fun render(priority: Int, tag: String, msg: String, fileLink: String, tr: Throwable?) {
        val stackTrace: String
        if (tr == null) {
            stackTrace = ""
        } else {
            stackTrace = Log.getStackTraceString(tr)
        }

        if (msg.length + fileLink.length + stackTrace.length < MAX_LOG_LENGTH) {
            println(priority, tag, msg + "\n" + fileLink + "\n" + stackTrace)
            return
        }

        val msgLength = msg.length
        var start = 0
        while (start < msgLength) {
            val end = start + breakText(msg, start, MAX_LOG_LENGTH)
            val part = msg.substring(start, end)
            if (end >= msgLength) {
                if (part.length + fileLink.length + stackTrace.length < MAX_LOG_LENGTH) {
                    println(priority, tag, part + "\n" + fileLink + "\n" + stackTrace)
                } else if (part.length + fileLink.length < MAX_LOG_LENGTH) {
                    println(priority, tag, part + "\n" + fileLink)
                    println(priority, tag, stackTrace)
                } else {
                    println(priority, tag, part)
                    println(priority, tag, fileLink + "\n" + stackTrace)
                }
            } else {
                println(priority, tag, part)
            }
            start = end
        }
    }

    fun v(msg: String) {
        if (!isEnabled(Log.VERBOSE)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.VERBOSE, tag, msg, fileLink, null)
    }

    fun v(msg: String, tr: Throwable) {
        if (!isEnabled(Log.VERBOSE)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.VERBOSE, tag, msg, fileLink, tr)
    }

    fun d(msg: String) {
        if (!isEnabled(Log.DEBUG)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.DEBUG, tag, msg, fileLink, null)
    }

    fun d(msg: String, tr: Throwable) {
        if (!isEnabled(Log.DEBUG)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.DEBUG, tag, msg, fileLink, tr)
    }

    fun i(msg: String) {
        if (!isEnabled(Log.INFO)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.INFO, tag, msg, fileLink, null)
    }

    fun i(msg: String, tr: Throwable) {
        if (!isEnabled(Log.INFO)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.INFO, tag, msg, fileLink, tr)
    }

    fun w(msg: String) {
        if (!isEnabled(Log.WARN)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.WARN, tag, msg, fileLink, null)
    }

    fun w(msg: String, tr: Throwable) {
        if (!isEnabled(Log.WARN)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.WARN, tag, msg, fileLink, tr)
    }

    fun e(msg: String) {
        if (!isEnabled(Log.ERROR)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.ERROR, tag, msg, fileLink, null)
    }

    fun e(msg: String, tr: Throwable) {
        if (!isEnabled(Log.ERROR)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.ERROR, tag, msg, fileLink, tr)
    }

    fun wtf(msg: String) {
        if (!isEnabled(Log.ASSERT)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.ASSERT, tag, msg, fileLink, null)
    }

    fun wtf(msg: String, tr: Throwable) {
        if (!isEnabled(Log.ASSERT)) {
            return
        }
        val callerInfo = callerInfo
        val tag = createTag(callerInfo)
        val fileLink = createFileLink(callerInfo)
        render(Log.ASSERT, tag, msg, fileLink, tr)
    }
}
