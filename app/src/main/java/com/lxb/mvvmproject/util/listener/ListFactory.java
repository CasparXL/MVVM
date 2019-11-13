package com.lxb.mvvmproject.util.listener;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

import com.lxb.mvvmproject.util.LogUtil;


public class ListFactory<T> {
    //动态变量，在GC回收时可能会被回收掉，避免持久引用
    public ObservableList.OnListChangedCallback<ObservableList<T>> getCallback(RecyclerView.Adapter adapter) {
        return new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
                if (sender != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                LogUtil.e("onItemRangeChanged:" + positionStart + "," + itemCount);
                if (sender != null) {
                    adapter.notifyItemRangeChanged(positionStart, itemCount);
                }
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                LogUtil.e("onItemRangeInserted:" + positionStart + "," + itemCount);
                if (sender != null) {
                    adapter.notifyItemRangeInserted(positionStart, itemCount);
                }
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                LogUtil.e("onItemRangeMoved:" + fromPosition + "," + toPosition + "," + itemCount);
                if (sender != null) {
                    adapter.notifyItemMoved(fromPosition, toPosition);
                }
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                LogUtil.e("onItemRangeRemoved:" + positionStart + "," + itemCount);
                if (sender != null) {
                    adapter.notifyItemRangeRemoved(positionStart, itemCount);
                }
            }
        };
    }

    //静态引用，不会被销毁，请注意使用
    public static ObservableList.OnListChangedCallback getListChangedCallback(RecyclerView.Adapter adapter) {
        return new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                if (sender != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                LogUtil.e("onItemRangeChanged:" + positionStart + "," + itemCount);
                if (sender != null) {
                    adapter.notifyItemRangeChanged(positionStart, itemCount);
                }
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                LogUtil.e("onItemRangeInserted:" + positionStart + "," + itemCount);
                if (sender != null) {
                    adapter.notifyItemRangeInserted(positionStart, itemCount);
                }
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                LogUtil.e("onItemRangeMoved:" + fromPosition + "," + toPosition + "," + itemCount);
                if (sender != null) {
                    adapter.notifyItemMoved(fromPosition, toPosition);
                }
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                LogUtil.e("onItemRangeRemoved:" + positionStart + "," + itemCount);
                if (sender != null) {
                    adapter.notifyItemRangeRemoved(positionStart, itemCount);
                }
            }
        };
    }
}
