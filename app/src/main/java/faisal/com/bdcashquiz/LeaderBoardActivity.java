package faisal.com.bdcashquiz;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {
    FirebaseFirestore db;
    private List<Item> arrayList;
    private ListView listView;
    private BaseAdapter adapter;
    String TAG="firebaseChack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        listView=(ListView) findViewById(R.id.listviewId);

        db = FirebaseFirestore.getInstance();

        arrayList=new ArrayList<Item>();
        adapter=new BaseAdapter() {
            LayoutInflater inflater= (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return arrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView==null){
                    convertView=inflater.inflate(R.layout.child_layout,null);
                }
                TextView balance=(TextView) convertView.findViewById(R.id.balanceId);
                TextView name=(TextView) convertView.findViewById(R.id.nameId);
                ImageView image=(ImageView) convertView.findViewById(R.id.imgview);

                balance.setText(arrayList.get(position).getBalance()+" ৳ ");
                name.setText(arrayList.get(position).getName());

                Glide.with(getBaseContext()).load(arrayList.get(position).getImg()).override(150,150).fitCenter().into(image);
               // image.setText(arrayList.get(position).getImg());
                return convertView;
            }
        };

        listView.setAdapter(adapter);
        //refreshDataView();
        userSort();

        adapter.notifyDataSetChanged();



    }
//    public void refreshDataView(){
//        db.collection("users")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot documentSnapshots) {
//                        List<DocumentSnapshot>docList=documentSnapshots.getDocuments();
//                        for (DocumentSnapshot dc:docList){
//                            Log.d(TAG,dc.getString("Balance"));
//
//
//                        }
//                    }
//                });
//    }


//    public void refreshDataView(){
//        arrayList.removeAll(arrayList);
//
//        db.collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//
//                                String balance = "" + document.getString("Balance");
//                                String name = "" + document.getString("Name");
//                                String img = "" + document.getString("Image");
//
//
//
//                                Item item = new Item(balance,name,img);
//                                arrayList.add(item);
//                            }
//
//                            adapter.notifyDataSetChanged();
//                            Log.d(TAG, "" + arrayList.toString());
//                        } else {
//                            Log.w(TAG, "Error getting documents.");
//                        }
//                    }
//                });
//    }


    private void userSort() {
        db.collection("userManage").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {

                List<DocumentSnapshot> docList = documentSnapshots.getDocuments();
                List<String> addlist = new ArrayList<String>();
                List<String> addlist2 = new ArrayList<String>();
                List<String> addlist3 = new ArrayList<String>();

                    /*List<Integer> cklist=new ArrayList<Integer>();
                    cklist.add(1);
                    cklist.add(2);
                    cklist.add(3);
                    cklist.add(4);
                    cklist.add(5);*/

                for (DocumentSnapshot dc : docList) {
                   /*Log.d("userCheck", dc.getId() + ">>>" + dc.getData().get("balance"));*/
        try {


                    addlist.add(String.valueOf(dc.getData().get("name")));

                    addlist2.add(String.valueOf(dc.getData().get("balance")));
                    addlist3.add(String.valueOf(dc.getData().get("photoUrl")));}

                    catch (Exception e){

                        Log.d("datGet",e.toString());
                    }

                }


                int length = addlist2.size();
                double temp = 0;
                String temp2 = "";
                String temp3 = "";
                for (int i = 0; i < length; i++) {
                    for (int j = 1; j < length - i; j++) {




                        try {
                            double var1 = Double.valueOf(addlist2.get(j - 1));

                            String lsr=String.valueOf(var1);

                            String name1 = addlist.get(j - 1);
                            String img1 = addlist3.get(j - 1);

                            double var2 = Double.valueOf(addlist2.get(j));
                            String name2 = addlist.get(j);
                            String img2 = addlist3.get(j);


                            if (var1 < var2) {
                                temp = var1;
                                temp2 = name1;
                                temp3 = img1;
                                /*Log.d("datGet",String.valueOf(var2));*/
                                addlist2.set(j - 1, String.valueOf(var2));
                                addlist.set(j - 1, name2);
                                addlist3.set(j - 1, img2);

                                addlist2.set(j, String.valueOf(temp));
                                addlist.set(j, temp2);
                                addlist3.set(j, temp3);


                            }
                        }

                       catch (Exception e){

                       }



                    }

                }

                for (int i = 0; i < addlist2.size(); i++) {
                   // String balance=addlist2.get(i);


                    String name=addlist.get(i);
                    String balance=addlist2.get(i);
                    String img=addlist3.get(i);

                    Log.d("datGet",String.valueOf(name)+">>>"+balance+">>>"+img);
                    Item item=new Item(balance,name,img);
                    arrayList.add(item);


                }
                adapter.notifyDataSetChanged();


                  //  Log.d("ShortList", arrayList.toString() );


            }
        });
    }

}
