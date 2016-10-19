package com.example.sanfuproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.example.sanfuproject.R;
import com.example.sanfuproject.activity.adapters.FreshGridAdapter;
import com.example.sanfuproject.activity.entity.Search;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.example.sanfuproject.activity.utils.Constants.goodsSearch;

public class SearchActivity extends AppCompatActivity {
    private String searchJson;
    private LinkedList<Map<String, Object>> data = new LinkedList<>();
    private FreshGridAdapter adapter;
    private PullToRefreshGridView mPullRefreshGridView;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
        initView();
    }

    private void initData() {

        goodsSearch = "http://m.sanfu.com/app/goods/goodsList.htm?goods.class_id=&page=" + i + "&pageSize=40&goods.search_words=" + getIntent().getStringExtra("keyword") + "&goods.is_disc=0&goods.is_hot=0&goods.is_new=2&goods.is_best=0&sid=a9f809a7c00111dd3abc3d49a06da2e4&source=1&key=lflrnhsahuogcuy&sign=E88688DE70D40C1B098A2BCABA4756C7";
//        System.out.println("--" + goodsSearch);

        int[] arr1 = new int[]{R.id.goods_list_txv_is_zong, R.id.goods_list_txv_is_new, R.id.goods_list_txv_is_hot, R.id.goods_list_txv_is_best};
//        TextView comprehensive = (TextView) findViewById(R.id.goods_list_txv_is_zong);
//        TextView thelatest = (TextView) findViewById(R.id.goods_list_txv_is_new);
//        TextView sales = (TextView) findViewById(R.id.goods_list_txv_is_hot);
//        TextView price = (TextView) findViewById(R.id.goods_list_txv_is_best);
        final String[] arr2 = new String[]{
                "http://m.sanfu.com/app/goods/goodsList.htm?goods.class_id=&page=1&pageSize=40&goods.search_words="
                        + getIntent().getStringExtra("keyword") +
                        "&goods.is_disc=0&goods.is_hot=0&goods.is_new=2&goods.is_best=0&sid=a9f809a7c00111dd3abc3d49a06da2e4&source=1&key=lflrnhsahuogcuy&sign=E88688DE70D40C1B098A2BCABA4756C7",

                "http://m.sanfu.com/app/goods/goodsList.htm?goods.class_id=&page=1&pageSize=40&goods.search_words="
                        + getIntent().getStringExtra("keyword") +
                        "&goods.is_disc=0&goods.is_hot=0&goods.is_new=1&goods.is_best=0&sid=a9f809a7c00111dd3abc3d49a06da2e4&source=1&key=svrplphgchbulvy&sign=428696F4E56279E53D7228D16574D80B",

                "http://m.sanfu.com/app/goods/goodsList.htm?goods.class_id=&page=1&pageSize=40&goods.search_words="
                        + getIntent().getStringExtra("keyword") +
                        "&goods.is_disc=0&goods.is_hot=1&goods.is_new=0&goods.is_best=0&sid=a9f809a7c00111dd3abc3d49a06da2e4&source=1&key=spgwestqnqgeqjy&sign=A22560FD995862184C49BF73BFE20080",

                "http://m.sanfu.com/app/goods/goodsList.htm?goods.class_id=&page=1&pageSize=40&goods.search_words="
                        + getIntent().getStringExtra("keyword") +
                        "&goods.is_disc=0&goods.is_hot=0&goods.is_new=0&goods.is_best=1&sid=a9f809a7c00111dd3abc3d49a06da2e4&source=1&key=ctynkuqoogsdkmj&sign=448C25B432663D981CBF3A85ADF0DDC0",

        };
//        TextView textView1 = (TextView) findViewById(arr1[0]);
//        textView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goodsSearch=arr2[0];
//            }
//        });


        RequestParams params = new RequestParams(goodsSearch);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String s) {
                searchJson = s;
                getData();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getData() {
        Search search = JSON.parseObject(searchJson, Search.class);
        List<Search.MsgBean.GoodsInfosBean> goodsInfos = search.getMsg().getGoodsInfos();
        for (Search.MsgBean.GoodsInfosBean bean : goodsInfos) {
            Map<String, Object> map = new HashMap<>();
            map.put("l_img", bean.getL_img());
            map.put("goods_name", bean.getGoods_name());
            map.put("mb_price", bean.getMb_price());
            data.add(map);
        }
        adapter.notifyDataSetChanged();
        mPullRefreshGridView.onRefreshComplete();
//        System.out.println("--get" + data);
    }

    private void initView() {
        //初始化搜索框，设置关键字
//        Intent intent = getIntent();
//        keyword = intent.getStringExtra("keyword");
//
//        EditText editText = (EditText) findViewById(R.id.search_activity_edit);
//        editText.setText(keyword);

        //初始化gridview，添加适配器加载数据
        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.goods_list_gridview);
        //设置空视图
        View view = findViewById(R.id.goods_list_linearlayout_no_data);
        mPullRefreshGridView.setEmptyView(view);

//        GridView mGridView = mPullRefreshGridView.getRefreshableView();
        //设置适配器
        adapter = new FreshGridAdapter(this, data);
        mPullRefreshGridView.setAdapter(adapter);
        ILoadingLayout startLabels = mPullRefreshGridView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("请往下拉...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新，请稍候...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = mPullRefreshGridView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("请往下拉...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在刷新，请稍候...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        // 设置监听器，这个监听器是可以监听双向滑动的，这样可以触发不同的事件
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
//
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
//
                i++;
                initData();
            }

        });
    }

}
