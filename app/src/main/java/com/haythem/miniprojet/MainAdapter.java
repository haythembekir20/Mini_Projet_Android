package com.haythem.miniprojet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {
      holder.marque.setText(model.getMarque());
      holder.modele.setText(model.getModele());
      holder.specs.setText(model.getSpecs());

        Glide.with(holder.img.getContext())
                .load(model.getImage())
                /*.placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)*/
                .into(holder.img);


          holder.btnEdit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();



                  View view = dialogPlus.getHolderView();
                  EditText marque = view.findViewById(R.id.txtMarque);
                  EditText modele = view.findViewById(R.id.txtModele);
                  EditText specs = view.findViewById(R.id.txtSpecs);
                  EditText image = view.findViewById(R.id.txtImg);
                  Button btnUpdate = view.findViewById(R.id.btnUpdate);
                  marque.setText(model.getMarque());
                  modele.setText(model.getModele());
                  specs.setText(model.getSpecs());
                  image.setText(model.getImage());
                  dialogPlus.show();

                  btnUpdate.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Map<String,Object> map = new HashMap<>();
                          map.put("Marque",marque.getText().toString());
                          map.put("Modele",modele.getText().toString());
                          map.put("Specs",specs.getText().toString());
                          map.put("Image",image.getText().toString());

                          FirebaseDatabase.getInstance().getReference().child("Phones")
                                  .child(Objects.requireNonNull(getRef(position).getKey()))
                                  .updateChildren(map)
                                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void unused) {
                                          Toast.makeText(holder.marque.getContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                                      dialogPlus.dismiss();
                                      }
                                  }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(Exception e) {
                                  Toast.makeText(holder.marque.getContext(),"Error while Updating",Toast.LENGTH_LONG).show();
                              }
                          });
                      }
                  });

              }
          });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.marque.getContext());
                builder.setTitle("Are you sure ?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("Phones")
                                    .child(Objects.requireNonNull(getRef(position).getKey()))
                                    .removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.marque.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
      CircleImageView img;
      TextView marque , modele , specs;
      Button btnEdit,btnDelete;


      public myViewHolder(@NonNull View itemView){
          super(itemView);

         img = (CircleImageView) itemView.findViewById(R.id.img1);
         marque = (TextView) itemView.findViewById(R.id.marque);
         modele = (TextView) itemView.findViewById(R.id.modele);
         specs = (TextView) itemView.findViewById(R.id.specs);
         btnEdit =(Button) itemView.findViewById(R.id.edit);
         btnDelete = (Button) itemView.findViewById(R.id.delete);

      }

    }
}
