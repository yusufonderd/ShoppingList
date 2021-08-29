package com.yonder.addtolist.common.utils.keyboard

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.FragmentActivity

private const val RATIO_KEYBOARD_VISIBILITY = 0.25

object KeyboardVisibilityEvent {

  private var isKeyboardShowing = false
  private var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null
  fun registerEventListener(activity: FragmentActivity?,
    keyboardStatus: (isOpen: Boolean) -> Unit) {
    unRegisterEventListener(activity)
    activity?.findViewById<View>(android.R.id.content)?.let { activityRootView ->
      globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val rect = Rect().apply { activityRootView.getWindowVisibleDisplayFrame(this) }
        val heightRootView = activityRootView.rootView.height
        val heightDiff = heightRootView - rect.height()
        if (heightDiff > RATIO_KEYBOARD_VISIBILITY * heightRootView) {
          if (!isKeyboardShowing) {
            isKeyboardShowing = true
            keyboardStatus(true)
          }
        } else {
          if (isKeyboardShowing) {
            isKeyboardShowing = false
            keyboardStatus(false)
          }
        }
      }
      activityRootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }
  }

  fun unRegisterEventListener(activity: FragmentActivity?) {
    globalLayoutListener?.let {
      activity?.findViewById<View>(
        android.R.id.content)?.viewTreeObserver?.removeOnGlobalLayoutListener(
        globalLayoutListener)
      globalLayoutListener = null
    }
  }

}
