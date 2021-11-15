package com.and.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;

import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new Adapter();
        rvList = findViewById(R.id.rvList);
        rvList.setAdapter(adapter);


        getList();
    }

    private void getList(){
        Retrofit re = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        PService service = re.create(PService.class);
        Call<List<picsumVO>> call = service.getPList();
        call.enqueue(new Callback<List<picsumVO>>() {
            @Override
            public void onResponse(Call<List<picsumVO>> call, Response<List<picsumVO>> response) {
                List<picsumVO> list = response.body();
                adapter.setList(list);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<picsumVO>> call, Throwable t) {

            }
        });

    }



}






class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private List<picsumVO> list;
    //setter 필요
    public void setList(List<picsumVO> list) {
        this.list = list;
    }

    @NonNull
    @Override  //레이아웃 객체화
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_picsum,parent,false);
        return new MyViewHolder(v);
    }

    @Override //뷰홀더에 데이터를 바인딩
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        picsumVO data = list.get(position);
        holder.setItem(data);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class MyViewHolder extends  RecyclerView.ViewHolder{
        private ImageView img;
        private TextView author;

        public MyViewHolder(@NonNull View V) {
            super(V);
            img = V.findViewById(R.id.img);
            author = V.findViewById(R.id.author);
        }

        public void  setItem(picsumVO p){
            author.setText(p.getAuthor());
            Glide.with(img).load(p.getDownload_url()).into(img);
        }
    }




}