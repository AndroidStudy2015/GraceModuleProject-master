//package com.yingdou.www.toucheventtest;
//
//import android.view.MotionEvent;
//
//public class WeiDaiMa {
//
//    WeiDaiMa child;
//
//
//    /**
//     * ViewGroup的dispatchTouchEvent
//     * 用来分发触摸事件
//     * 虽然ViewGroup继承自View，但是他重写了dispatchTouchEvent方法，所以这里区分一下
//     *
//     * @param ev
//     * @return
//     */
//
//    public boolean dispatchTouchEvent4ViewGroup(MotionEvent ev) {
//        Boolean consume = false;
//
//        if (当前ViewGroup要不要拦截事件(ev)) {//如果本ViewGroup拦截事件，那么调用本ViewGroup的onTouchEvent
//            consume = onTouchEvent(ev);
//        } else {//否则，调用子View的dispatchTouchEvent，子ViewGroup的dispatchTouchEvent逻辑是一样的
//            consume = child.dispatchTouchEvent4ViewGroup(ev);////如果child为viewgroup
////            consume = child.dispatchTouchEvent4View(ev);//如果child为view
//        }
//
//        return consume;//返回true，表示事件被消耗了，
//        // 如果是最里层级的view或ViewGroup的dispatchTouchEvent返回true，表示由本ViewGroup来处理以后的事件
//
//    }
//
//    private boolean 当前ViewGroup要不要拦截事件(MotionEvent ev) {
//        boolean intercepted;
//        if (ev.getAction() == MotionEvent.ACTION_DOWN
//                || 本ViewGroup有子View_且_本ViewGroup不拦截事件_交给了子View处理_且_子View的dispatchTouchEvent返回true_即子View处理了事件) {
//            if (子View请求不拦截_requestDisallowInterceptTouchEvent(true)) {
//                //询问本ViewGroup的onInterceptTouchEvent，要不要拦截事件
//                intercepted = onInterceptTouchEvent(ev);
//            } else {
//                intercepted = false;//如果子View没有请求不拦截，那么默认返回false
//            }
//        } else {
//            //当这个事件不是ACTION_DOWN，并且当前的ViewGroup也没有子ViewGroup（view）可以处理事件，那么就由本ViewGroup拦截这个事件
//            intercepted = true;
//        }
//        return intercepted;
//    }
//
//    /**
//     * ViewGroup 和 View 的onTouchEvent是一样的 都是view的，
//     * 因为ViewGroup是view的子类，且ViewGroup没有重写view的onTouchEvent
//     * 用来处理触摸事件
//     *
//     * @param ev
//     * @return
//     */
//    private Boolean onTouchEvent(MotionEvent ev) {
//
////            如果控件可点击返回true，否则返回false
//        if (isClickable || isLongClickable) {
////            onTouchListener.onTouch--优先于-》onTouchEvent-优先于--》onClick
////            执行click接口
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * ViewGroup特有的方法
//     * 用来判断是否拦截触摸事件
//     * 这个方法不一定每次都要都要，他是有条件的 ，见 《当前ViewGroup要不要拦截事件》
//     *
//     * @param ev
//     * @return
//     */
//    private boolean onInterceptTouchEvent(MotionEvent ev) {
//
////默认返回false，
//// 可以在这里重写，定义在down时候，返回false不拦截
//// down up事件返回true拦截   配合子view的requestDisallowIntenceot方法
////        其实requestDisallowInterceptTouchEvent(true)）方法的目的，只是通知父view去询问下自己的onInterceptTouchEvent方法，看看要不要拦截
////        如果requestDisallowInterceptTouchEvent(fasle),就不会询问自己的onInterceptTouchEvent方法，直接拦击了
//        return false;
//    }
//
//
////    ===================================================================================================
//
//    /**
//     * View的dispatchTouchEvent
//     * 用来分发触摸事件
//     *
//     * @param ev
//     * @return
//     */
//
//    public boolean dispatchTouchEvent4View(MotionEvent ev) {
////        如果控件可用&&设置了mOnTouchListener，&&onTouch方法返回true,该方法直接返回true，不去调用onTouchEvent方法
//        if (mOnTouchListener != null && enable && mOnTouchListener.onTouch(ev)) {
//            return true;
//        } else {
//            return onTouchEvent(ev);
//        }
//
//
//    }
//
//
//}
