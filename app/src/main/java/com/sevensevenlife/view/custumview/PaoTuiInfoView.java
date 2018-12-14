package com.sevensevenlife.view.custumview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.LinearLayout;

import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.model.httpmodel.ServiceDeatil;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.view.DiaLog.ImgDialog;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityPaotuiInfoBinding;
import com.example.youxiangshenghuo.databinding.ImgLayoutBinding;

import java.util.List;

public class PaoTuiInfoView {
    private ActivityPaotuiInfoBinding binding;

    public PaoTuiInfoView( ActivityPaotuiInfoBinding binding) {
        this.binding = binding;
    }


    public void initGalleryView(final Context context, final List<String> list) {
        if (list.size() == 0) {
            return;
        }
        binding.bottom.listLayout.setVisibility(View.VISIBLE);
        new RViewUtils(context, binding.bottom.list)
                .setLayout(1, LinearLayout.HORIZONTAL)
                .setAdapter(new PublicAdapter<>(list, R.layout.img_layout, new BindViewInterface() {
                    @Override
                    public void bandView(ViewDataBinding bindings, final int position) {
                        ImgLayoutBinding bin = (ImgLayoutBinding) bindings;
                        ImgLoadUtils.Load(context, list.get(position), bin.img, false);
                        bin.img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImgDialog.init(context)
                                        .setImg(list.get(position))
                                        .show();
                            }
                        });
                    }
                }));
//        binding.bottom.gallery.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                DialogImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
//                        R.layout.dialog_image, null, false);
//                ImgLoadUtils.Load(context, list.get(arg2), binding.image, false);
//                binding.image.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        myDialogView.dismiss();
//                    }
//                });
//                myDialogView.show(binding.getRoot());
//            }
//        });

    }


    public void setTopBottomValue(Context context, ServiceDeatil map) {
        //最顶部
        binding.top.paoduiName.setText(map.getRows().getRealName());

        binding.top.appraisalTotal.setText(map.getRows().getAppraisalTotal());
        if (map.getRows().getAuthenticate().equals("1")) {
            binding.top.authenticate.setText("七七认证 ");
        } else {
            binding.top.authenticate.setText("未认证 ");
        }

        binding.top.orderCount.setText(map.getRows().getOrderCount());
        binding.top.td.setText("态度：" + map.getRows().getAttitude());
        binding.top.zy.setText("专业：" + map.getRows().getProfession());
        binding.top.sh.setText("守时：" + map.getRows().getPunctual());


        binding.bottom.fwjj.setText(map.getRows().getIntroduction());

        ImgLoadUtils.Load(context, map.getRows().getHeadPic(), binding.top.topImage, true);
    }

    public void setCenterValue(Context context, ServiceDeatil bean) {
        String appraisalCount;
        try {
            appraisalCount = bean.getRows().getAppraisalCount();
        } catch (Exception e) {
            appraisalCount = "0";
        }
        if (appraisalCount.equals("0") || appraisalCount.equals("")) {
            binding.center.layoutCenter.setVisibility(View.GONE);
        } else {
            binding.center.layoutCenter.setVisibility(View.VISIBLE);
            binding.center.appraisalCount.setText("顾客评价：" + appraisalCount);
            binding.center.centerNickName.setText(bean.getRows().getAppraisalList().get(0).getCustName());
            binding.center.createDate.setText(bean.getRows().getAppraisalList().get(0).getCreateDate());
            binding.center.content.setText(bean.getRows().getAppraisalList().get(0).getContent());
            ImgLoadUtils.Load(context, bean.getRows().getAppraisalList().get(0).getCustPic(), binding.center.centerImage, true);
        }
    }

}
