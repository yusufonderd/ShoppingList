package com.yonder.addtolist.scenes.premium

/**
 * @author yusuf.onder
 * Created on 11.11.2021
 */

// https://github.com/Zhuinden/fragmentviewbindingdelegate-kt
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.yonder.addtolist.common.ui.base.BaseFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
  val fragment: Fragment,
  val viewBindingFactory: (View) -> T,
  val viewProvider: Fragment.() -> View = { requireView() }
) : ReadOnlyProperty<Fragment, T> {
  internal var binding: T? = null

  init {
    fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
      override fun onCreate(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
          viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
              binding = null
            }
          })
        }
      }
    })
  }

  override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
    val binding = binding
    if (binding != null) {
      return binding
    }

    val lifecycle = fragment.viewLifecycleOwner.lifecycle
    if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
      throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
    }

    return viewBindingFactory(thisRef.viewProvider()).also { this.binding = it }
  }
}

fun <T : ViewBinding> Fragment.viewBinding(
  viewBindingFactory: (View) -> T
) = FragmentViewBindingDelegate(this, viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
  crossinline viewBindingFactory: (LayoutInflater) -> T
) = unsafeLazy {
  viewBindingFactory(layoutInflater)
}

fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
