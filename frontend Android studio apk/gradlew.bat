/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.core.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Detects various gestures and events using the supplied {@link MotionEvent}s.
 * The {@link OnGestureListener} callback will notify users when a particular
 * motion event has occurred. This class should only be used with {@link MotionEvent}s
 * reported via touch (don't use for trackball events).
 *
 * <p>This compatibility implementation of the framework's GestureDetector guarantees
 * the newer focal point scrolling behavior from Jellybean MR1 on all platform versions.</p>
 *
 * To use this class:
 * <ul>
 *  <li>Create an instance of the {@code GestureDetectorCompat} for your {@link View}
 *  <li>In the {@link View#onTouchEvent(MotionEvent)} method ensure you call
 *          {@link #onTouchEvent(MotionEvent)}. The methods defined in your callback
 *          will be executed when the events occur.
 * </ul>
 */
public final class GestureDetectorCompat {
    interface GestureDetectorCompatImpl {
        boolean isLongpressEnabled();
        boolean onTouchEvent(MotionEvent ev);
        void setIsLongpressEnabled(boolean enabled);
        void setOnDoubleTapListener(OnDoubleTapListener listener);
    }

    static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
   