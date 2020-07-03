/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.litho

/** Base class for Kotlin Components. */
open class KComponent private constructor(
    private val content: (DslScope.() -> Component?)? = null,
    private val style: Style? = null
) : Component() {
  constructor(style: Style? = null) : this(null, style)
  constructor(content: DslScope.() -> Component?) : this(content, null)

  override fun onCreateLayout(c: ComponentContext): Component? {
    return DslScope(c).run {
      render()?.apply {
        applyStyle(style)
      }
    }
  }

  open fun DslScope.render(): Component? {
    val render = requireNotNull(content) {
      "You should override `render()` method or provide `content` lambda via constructor."
    }
    return render()
  }
}
