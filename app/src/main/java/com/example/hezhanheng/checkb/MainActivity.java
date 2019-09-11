package com.example.hezhanheng.checkb;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Button button1,button2,button3,button4,button5;
    private ListView listView;
    private List<Bean> mDatas;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditList(button1);
            }
        });
        /*quan xuan */
        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSelectAllList(button2);
            }
        });
        /*  quan bu xuan */
        button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNoList(button3);
            }
        });
        /* fan xuan */
        button4=(Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnfanxuanList(button4);
            }
        });
        /* caozuo */
        button5=(Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOperateList(button5);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Bean bean = new Bean("" + i, "上邪", "山无棱，天地合，乃敢与君绝");
            mDatas.add(bean);
        }
        mAdapter = new MyAdapter(this, mDatas);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()) {
                    case R.id.listView:
                        Toast.makeText(MainActivity.this,"点击了第"+position+"个",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

//    /**
//     * 编辑、取消编辑
//     * @param view
//     */
    public void btnEditList(View view) {
        mAdapter.flage = !mAdapter.flage;
        if (mAdapter.flage) {
            button1.setText("取消");
        } else {
            button1.setText("编辑");
        }
        mAdapter.notifyDataSetChanged();
    }
    /**
     * 全选
     * @param view
     */
    public void btnSelectAllList(View view) {

        if (mAdapter.flage) {
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = true;
            }
            mAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 全不选
     * @param view
     */
    public void btnNoList(View view) {
        if (mAdapter.flage) {
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = false;
            }
            mAdapter.notifyDataSetChanged();
        }
    }



    /**

     * 反选

     * @param view

     */

    public void btnfanxuanList(View view) {
        if (mAdapter.flage) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isCheck) {
                    mDatas.get(i).isCheck = false;
                } else {
                    mDatas.get(i).isCheck = true;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }
    /**

     * 获取选中数据

     * @param view

     */

    public void btnOperateList(View view) {
        List<String> ids = new ArrayList<>();
        if (mAdapter.flage) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isCheck) {
                    ids.add(mDatas.get(i).id);
                }
            }
            Toast.makeText(MainActivity.this,ids.toString(), Toast.LENGTH_SHORT).show();
            Log.e("TAG", ids.toString());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    /*适配器*/
    public class MyAdapter extends BaseAdapter {
        private Context mContext;
        private List<Bean> mDatas;
        private LayoutInflater mInflater;
        public boolean flage = false;
        public MyAdapter(Context mContext, List<Bean> mDatas) {
            this.mContext = mContext;
            this.mDatas = mDatas;
            mInflater = LayoutInflater.from(this.mContext);
        }
        @Override
        public int getCount() {
            return mDatas.size();
        }
        @Override
        public Object getItem(int i) {
            return mDatas.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (convertView == null) {
                // 下拉项布局
                convertView = mInflater.inflate(R.layout.list_item_data, null);
                holder = new ViewHolder();
                holder.checkboxOperateData = (CheckBox) convertView.findViewById(R.id.checkbox_operate_data);
                holder.textTitle = (TextView) convertView.findViewById(R.id.text_title);
                holder.textDesc = (TextView) convertView.findViewById(R.id.text_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Bean Bean = mDatas.get(position);
            if (Bean != null) {
                holder.textTitle.setText(Bean.title);
                holder.textDesc.setText(Bean.desc);
                // 根据isSelected来设置checkbox的显示状况
                if (flage) {
                    holder.checkboxOperateData.setVisibility(View.VISIBLE);
                } else {
                    holder.checkboxOperateData.setVisibility(View.GONE);
                }
                holder.checkboxOperateData.setChecked(Bean.isCheck);
                //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
                holder.checkboxOperateData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Bean.isCheck) {
                            Bean.isCheck = false;
                        } else {
                            Bean.isCheck = true;
                        }
                    }
                });
            }
            return convertView;
        }
        class ViewHolder {
            public CheckBox checkboxOperateData;
            public TextView textTitle;
            public TextView textDesc;
        }
    }

}

