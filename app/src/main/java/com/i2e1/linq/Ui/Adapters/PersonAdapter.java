package com.i2e1.linq.Ui.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.i2e1.linq.R;
import com.i2e1.linq.Ui.CustomAppTextViews.CustomAppTextViewMedium;
import com.i2e1.linq.Ui.CustomAppTextViews.CustomAppTextViewRegular;
import com.i2e1.linq.Utils.UtilFunctions;
import com.i2e1.linq.data.Pojo.PersonWrapper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.viewHolder> {

    private Context context;
    /**
     * list of persons to be displayed
     */
    private List<PersonWrapper> listOfPersons;


    public PersonAdapter(Context context, List<PersonWrapper> listOfPersons) {
        this.context = context;
        this.listOfPersons = listOfPersons;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.persons_adapter_child_view, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int i) {

        PersonWrapper person= listOfPersons.get(i);

        if(person.getPictureImageData().length()==0){
            /**
             * loading image directly from url in case of not stored in database yet
             */
            Glide.with(context).load(person.getPictureUrl()).into(holder.mProfilePic);
        }else {
            /**
             * setting bitmap data from database to imageView
             */
            holder.mProfilePic.setImageBitmap(UtilFunctions.stringToBitmap(person.getPictureImageData()));
        }

        holder.mPersonName.setText(person.getFullName());
        holder.mPhoneNumber.setText(person.getPhoneNumber());
        holder.mEmail.setText(person.getEmail());
        /**
         * converting date time string to date format to show in designed layout
         */
        holder.mDob.setText(UtilFunctions.convertDateTimeToDateFormat(person.getDob()));
    }

    @Override
    public int getItemCount() {
        return listOfPersons.size();
    }

    /**
     * ViewHolder class of adapter
     */
    class viewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mProfilePic;
        private CustomAppTextViewMedium mPersonName;
        private CustomAppTextViewRegular mPhoneNumber, mEmail, mDob;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            mProfilePic=(CircleImageView)itemView.findViewById(R.id.civ_profile_photo);
            mPersonName = (CustomAppTextViewMedium) itemView.findViewById(R.id.cmtv_person_name);
            mPhoneNumber = (CustomAppTextViewRegular) itemView.findViewById(R.id.crtv_phone_number);
            mEmail = (CustomAppTextViewRegular) itemView.findViewById(R.id.crtv_email);
            mDob = (CustomAppTextViewRegular) itemView.findViewById(R.id.crtv_birthday);
        }
    }
}
