package com.zrz.android.spacebook.util.extension

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.getRequiredStringFromIntent(intentKey: String, bundleKey: String) =
    intent.getRequiredString(intentKey, bundleKey)

inline fun <reified A> AppCompatActivity.startNewActivity(
    intentKey: String,
    vararg extras: Pair<String, String>
)
    where A : AppCompatActivity {
    val intent = Intent(this, A::class.java)
    val list = listOf(*extras)
    if (list.isNotEmpty()) {
        val bundle = Bundle()
        list.forEach { (bundleKey, extra) ->
            bundle.putSerializable(
                bundleKey.asBundleKey(),
                extra
            )
        }
        intent.putExtra(intentKey.asIntentKey(), bundle)
    }
    startActivity(intent)
}

inline fun <reified ET> useInputText(source: ET, vararg actions: (String) -> Unit)
    where ET : EditText {
    val text = source.text.toString()
    if (text.isNotEmpty()) {
        source.text.clear()
        val list = listOf(*actions)
        list.forEach { it.invoke(text) }
    }
}