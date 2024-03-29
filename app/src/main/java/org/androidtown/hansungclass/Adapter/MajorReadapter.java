package org.androidtown.hansungclass.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.hansungclass.FirebaseClass.Login;
import org.androidtown.hansungclass.FirebaseClass.Major;
import org.androidtown.hansungclass.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hscom-019 on 2017-11-19.
         */

public class MajorReadapter extends RecyclerView.Adapter<MajorReadapter.ViewHolder>{
    public static int i=0;
    private static TextView md;

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView coursesubject;
        public TextView coursecredit;
        public TextView coursedivide;
        public TextView courseprofessor;
        public TextView coursenclass;
        public TextView coursentime;
        private int count = 2;
        private int llimitCount;
        public String color = "0";
        private Context context;
        private MajorReadapter majorReadapter;
        private DatabaseReference mDatabase;
        private DatabaseReference databaseReference;
        private DatabaseReference mConditionRef;
        private DatabaseReference subjectCount;
        private Button btn;
        private int colors[] = new int[15];
        private Set<String> keys;
        private Iterator<String> it;
        private Major major;
        private int timecount;
        private String datadays[];
        private String timer;
        private boolean check;
        private HashMap<String, Object> majorHashMap;
        private Map<String,Object> changedata;
        private Map<String,Object> changedata1;
        private Map<String,Object> changedata2;
        private int u_credit;
        private int total;

        public ViewHolder(Context context, View itemView, final MajorReadapter majorReadapter){
            super(itemView);
            this.context = context;
            this.majorReadapter = majorReadapter;
            coursesubject = (TextView)itemView.findViewById(R.id.coursesubject);
            coursecredit = (TextView)itemView.findViewById(R.id.coursecredit);
            coursedivide = (TextView)itemView.findViewById(R.id.coursedivide);
            courseprofessor = (TextView)itemView.findViewById(R.id.courseprofessor);
            coursenclass = (TextView)itemView.findViewById(R.id.courseclass);
            coursentime = (TextView)itemView.findViewById(R.id.coursetime);
            btn = (Button)itemView.findViewById(R.id.courseButton1);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mConditionRef = mDatabase.child("파이어베이스").child("강의").child(email);
            mConditionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            major = child.getValue(Major.class);
                            if(coursesubject.getText().equals(major.getSubject())){
                                count = major.getCount();
                                btn.setText("취소");
                            }
                        }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            mDatabase.child("파이어베이스").child("USER_INFO").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        Login login = child.getValue(Login.class);
                        String name[] = login.getEmail().split("@");
                        if(name[0].equals(email)){
                            total = login.getU_credit();
                        }
                    }
                    u_credit = total;
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            btn.setOnClickListener(this);
            colors[0] = Color.rgb(214, 252, 251);
            colors[1] = Color.rgb(252, 214, 248);
            colors[2] = Color.rgb(255, 185, 185);
            colors[3] = Color.rgb(214, 252, 251);
            colors[4] = Color.rgb(198, 198, 255);
            colors[5] = Color.rgb(255, 220, 185);
            colors[6] = Color.rgb(228, 228, 228);
            colors[7] = Color.rgb(255, 255, 204);
            colors[8] = Color.rgb(255, 213, 234);
            colors[9] = Color.rgb(231, 206, 255);
            colors[10] = Color.rgb(200, 255, 214);
            colors[11] = Color.rgb(255, 218, 181);
            colors[12] = Color.rgb(209, 209, 233);
            colors[13] = Color.rgb(196, 196, 255);
            colors[14] = Color.rgb(230, 204, 204);
        }
        @Override
        public void onClick(View v) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mConditionRef = mDatabase.child("파이어베이스").child("강의").child(email);
            llimitCount = 0;
            majorHashMap = new HashMap<>();
            majorHashMap.put("color", colors[MajorReadapter.i]);
            majorHashMap.put("count",count);
            majorHashMap.put("credit",coursecredit.getText().toString());
            majorHashMap.put("professor",courseprofessor.getText().toString());
            majorHashMap.put("divide",coursedivide.getText().toString());
            majorHashMap.put("nclass",coursenclass.getText().toString());
            majorHashMap.put("ntime",coursentime.getText().toString());
            majorHashMap.put("subject",coursesubject.getText().toString());
            Log.i("yunjae", "onClick i = " + MajorReadapter.i);

            timecount = 0;
            timer = majorHashMap.get("ntime").toString();
            datadays = timer.split(" "); //월 1,2,3
            check = true;
            mConditionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        major = child.getValue(Major.class);
                        String ntime = major.getNtime();
                        String firedataday[] = ntime.split(" "); //월 1,2,3
                        if(firedataday[0].equals(datadays[0])){
                            String firenumber[] = firedataday[1].split(",");
                            String datanumber[] = datadays[1].split(",");
                            for(int i=0;i<firenumber.length; i++){
                                for(int j=0;j<datanumber.length;j++){
                                    if(firenumber[i].equals(datanumber[j])){
                                        timecount++;
                                        check = false;
                                    }
                                }
                            }
                        }
                    }
                    MajorReadapter.i++;
                    /*
                       총학점 20학점 넘기면 수강신청 안댐!! //성공
                       총수강인원이 30명을 넘을시 수강신청 안댐!
                     */

                    if(timecount != 0 && btn.getText().equals("신청")){
                        Toast.makeText(context,"같은 시간대를 선택한 과목이 있습니다.",Toast.LENGTH_LONG).show();
                    }
                    else if(check == true && btn.getText().equals("신청")){
                        if(u_credit < 20) {
                            Toast.makeText(context, "수강신청 성공!", Toast.LENGTH_LONG).show();
                            //changedata1 = new HashMap<String, Object>();
                            //count--;
                            //changedata1.put("count",count);
                            //llimitCount = 2;
                            //mDatabase.child("university").child("1").child("major").child()
                            changedata = new HashMap<String, Object>();
                            total += Integer.parseInt(coursecredit.getText().toString());
                            changedata.put("u_credit", total);
                            mDatabase.child("파이어베이스").child("USER_INFO").child(email).updateChildren(changedata);
                            mDatabase.child("파이어베이스").child("강의").child(email).child(coursesubject.getText().toString()).setValue(majorHashMap);


                            mDatabase.child("university").child("2018").child("1").child("major").child("computer").child("android").child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int count = dataSnapshot.getValue(Integer.class);
                                    mDatabase.child("university").child("2018").child("1").child("major").child("computer").child("android").child("count").setValue(--count);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            btn.setText("취소");

                        }
                        else{
                            Toast.makeText(context,"학점을 초과하였습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(check == false && btn.getText().equals("취소")){
                        Toast.makeText(context, "수강신청 취소!", Toast.LENGTH_LONG).show();
                        changedata = new HashMap<String,Object>();
                        total -= Integer.parseInt(coursecredit.getText().toString());
                        changedata.put("u_credit",total);
                        mDatabase.child("파이어베이스").child("USER_INFO").child(email).updateChildren(changedata);
                        mDatabase.child("파이어베이스").child("강의").child(email).child(coursesubject.getText().toString()).setValue(null);
                        btn.setText("신청");
                        check = true;
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });



        }
    }
    private Context context;
    private List<Major> majorList;
    private Major major;
    private String email;
    public MajorReadapter(Context context, List<Major> majorList, String email){
        this.context = context;
        this.majorList = majorList;
        this.email = email;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.enrollment_course,parent,false);
        ViewHolder viewHolder = new ViewHolder(context,view,this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        major = majorList.get(position);
        TextView subject = holder.coursesubject;
        subject.setText(major.getSubject());
        TextView credit = holder.coursecredit;
        credit.setText(major.getCredit());
        TextView divide = holder.coursedivide;
        divide.setText(major.getDivide());
        TextView professor = holder.courseprofessor;
        professor.setText(major.getProfessor());
        TextView nclass = holder.coursenclass;
        nclass.setText(major.getNclass());
        TextView ntime = holder.coursentime;
        ntime.setText(major.getNtime());
    }

    @Override
    public int getItemCount() {
        return majorList.size();
    }
}
