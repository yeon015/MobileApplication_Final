package ddwu.mobile.finalproject.ma02_20200987;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlaceAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Place> placeList;
    private LayoutInflater layoutInflater;

    public PlaceAdapter(Context context, int layout, ArrayList<Place> placeList) {
        this.context = context;
        this.layout = layout;
        this.placeList = placeList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return placeList.size();
    }

    @Override
    public Object getItem(int i) {
        return placeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return placeList.get(i).get_Id();
    }

    @Override   //원본 데이터의 개수만큼 반복 호출 (pos가 몇번째 View를 요청하는지 나타내는 변수. pos에 해당하는 데이터 가져옴)
    public View getView(int pos, View convertView, ViewGroup viewGroup) { //convertview-> 보여질 화면. 만들어져서 반환할 화면. (전달받은 뷰) 맨 처음엔 뷰를 만든 적 없기에 빈(껍데기) 뷰가 들어옴. 여기에 레이아웃으로 화면을 만들어서 데이터를 채워넣어 리턴시켜야함
        final int position = pos;   //함수 종료후 매개변수 없어지므로 상수로 만들어줌. ex) 나중에 버튼 눌렀을때 위치 확인 못하기에 필요

        //스크롤 개선 위함
        ViewHolder holder;

        //view가 비어있는지 아닌지 검사. 비어있을 경우 inflater를 사용해서 layout에 해당하는 view 객체 생성 (맨 처음에 null이 들어오기 때문에 실제 화면을 만드는 것)
        if(convertView == null) {
            convertView = layoutInflater.inflate(layout, viewGroup, false);  //새로 레이아웃 만듦.(숫자, 이름, 전화번호, 버튼이 들어있는)

            //2) 슬라이드 p.16
            //스크롤 개선 위함
            holder = new ViewHolder();
            holder.placeName = convertView.findViewById(R.id.placeName);
            holder.trash = convertView.findViewById(R.id.trash);
            convertView.setTag(holder);  //setTag에 보관
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.placeName.setText(placeList.get(position).getName());
        holder.trash.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;  //만든 객체 view 반환
    }

    //스크롤 속도 개선위함. 이제 이걸 사용!!
    static class ViewHolder{
        TextView placeName;
        ImageView trash;
    }
}
