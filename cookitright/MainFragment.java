package com.example.cookitright.fragments;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.cookitright.MainActivity;
import com.example.cookitright.R;
import com.example.cookitright.adapters.AllCookAdapter;
import com.example.cookitright.callback.CooksCallback;
import com.example.cookitright.databinding.CookDetailsDialogLayBinding;
import com.example.cookitright.databinding.FragmentMainBinding;
import com.example.cookitright.datamodels.AllCookData;
import com.example.cookitright.datamodels.CookData;
import com.example.cookitright.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;



public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, CooksCallback {

    private MainActivity activity;
    String imagePath;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionRef;
    private FirebaseAuth mAuth;
    private AllCookAdapter cookAdapter;
    private LinearLayoutManager layoutManager;
    private List<AllCookData> cookDataList;
    private FragmentMainBinding mBinding;

    public MainFragment() {
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater);
        inIt();
        return mBinding.getRoot();
    }
    private void inIt() {
        cookDataList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(activity);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBinding.rvCooks.setLayoutManager(layoutManager);
        mBinding.rvCooks.setHasFixedSize(true);
        mAuth = FirebaseAuth.getInstance();
        drawerLay();
        setUpNavDrawer();
        collectionRef = db.collection("Cooks");
        getCookDetails();
        mBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCookDialog();
            }
        });
        mBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = activity.findViewById(checkedId);
                String selectedOption = radioButton.getText().toString();
                switch (selectedOption) {
                    case "Search by Dish name":
                        mBinding.etSearchTitle.setVisibility(View.VISIBLE);
                        mBinding.etSearchAuthor.setVisibility(View.GONE);
                        mBinding.etSearchGenre.setVisibility(View.GONE);
                        Constant.saveData(activity, 1);
                        break;
                    case "Search by Chef name":
                        mBinding.etSearchAuthor.setVisibility(View.VISIBLE);
                        mBinding.etSearchTitle.setVisibility(View.GONE);
                        mBinding.etSearchGenre.setVisibility(View.GONE);
                        Constant.saveData(activity, 2);
                        break;
                    case "Search by Category":
                        mBinding.etSearchGenre.setVisibility(View.VISIBLE);
                        mBinding.etSearchTitle.setVisibility(View.GONE);
                        mBinding.etSearchAuthor.setVisibility(View.GONE);
                        Constant.saveData(activity, 3);
                        break;
                }
            }
        });

        mBinding.etSearchTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cookAdapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mBinding.etSearchAuthor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cookAdapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mBinding.etSearchGenre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cookAdapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void drawerLay() {
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.drawerLay.openDrawer(Gravity.LEFT);
            }
        });
    }
    private void getCookDetails() {

        ListenerRegistration listenerRegistration = collectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                cookDataList.clear();
                if (snapshot != null) {
                    for (DocumentSnapshot document : snapshot.getDocuments()) {
                        String documentId = document.getId();
                        cookDataList.add(new AllCookData(documentId, document.getString("cookName"), document.getString("chef")
                                , document.getString("cookCategory")
                                , document.getString("cookRecipe")
                                , document.getString("cookCoverImage")
                                , Boolean.TRUE.equals(document.getBoolean("read"))
                                , Boolean.TRUE.equals(document.getBoolean("current"))
                                , Boolean.TRUE.equals(document.getBoolean("finished"))));
                    }
                }
                setCookRecyclerView();
            }
        });
    }
    private void setCookRecyclerView() {
        cookAdapter = new AllCookAdapter(cookDataList, activity, this);
        if (cookDataList.isEmpty()){
            mBinding.tvNoData.setVisibility(View.VISIBLE);
        } else {
            mBinding.tvNoData.setVisibility(View.GONE);
            mBinding.rvCooks.setAdapter(cookAdapter);
        }
    }
    private void setUpNavDrawer() {
        mBinding.navView.bringToFront();
        mBinding.navView.setNavigationItemSelectedListener(this);
    }
    private void showCookDialog() {
        CookDetailsDialogLayBinding collectionBinding =
                CookDetailsDialogLayBinding.inflate(LayoutInflater.from(requireContext()));
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int deviceWidthPixels = displayMetrics.widthPixels;
        double desiredWidthPercentage = 0.8;
        int dialogWidthPixels = (int) (deviceWidthPixels * desiredWidthPercentage);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = dialogWidthPixels;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(collectionBinding.getRoot());
        Objects.requireNonNull(dialog.getWindow()).setAttributes(layoutParams);
        dialog.setCancelable(false);

        collectionBinding.ivCookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPhoto(collectionBinding);
            }
        });

        collectionBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials(collectionBinding, dialog);
            }
        });

        collectionBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void checkCredentials(CookDetailsDialogLayBinding collectionBinding, Dialog dialog) {

        String title = collectionBinding.etTitle.getText().toString().trim();
        String chef = collectionBinding.etAuthor.getText().toString().trim();
        String category = collectionBinding.etGenre.getText().toString().trim();
        String recipe = collectionBinding.etDescription.getText().toString().trim();
        if (imagePath == null) {
            Toast.makeText(activity, "Please add dish cover", Toast.LENGTH_SHORT).show();
            return;
        }
        if (title.isEmpty()) {
            collectionBinding.etTitle.setError("Field is required!");
            collectionBinding.etTitle.requestFocus();
            return;
        }
        if (chef.isEmpty()) {
            collectionBinding.etAuthor.setError("Field is required!");
            collectionBinding.etAuthor.requestFocus();
            return;
        }
        if (category.isEmpty()) {
            collectionBinding.etGenre.setError("Field is required!");
            collectionBinding.etGenre.requestFocus();
            return;
        }
        if (recipe.isEmpty()) {
            collectionBinding.etDescription.setError("Field is required!");
            collectionBinding.etDescription.requestFocus();
        } else {
            addCookDetailsToFirebase(title,chef, category ,recipe);
            dialog.dismiss();
        }}
    private void addCookDetailsToFirebase(String title, String chef , String category,String recipe) {

        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();

        CookData cookData = new CookData(title, chef, category ,recipe, imagePath, false, false, false);

        db.collection("Cooks")
                .add(cookData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dialog.dismiss();
                        String documentId = documentReference.getId();
                        Log.i("Firestore", "Document added with ID: " + documentId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Log.i("Firestore", "Error adding document", e);
                    }
                });
    }

    void addPhoto(CookDetailsDialogLayBinding collectionBinding) {
        TedImagePicker.with(activity)
                .start(new OnSelectedListener() {
                    @Override
                    public void onSelected(@NotNull Uri uri) {
                        showSingleImage(uri, collectionBinding);
                    }
                });
    }
    void showSingleImage(Uri uri, CookDetailsDialogLayBinding collectionBinding) {
        uploadPhoto(uri, collectionBinding);
    }

    private void uploadPhoto(Uri uri, CookDetailsDialogLayBinding collectionBinding) {
        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagesReference = storageReference.child("images");
        long currentTimeMillis = System.currentTimeMillis();
        String fileName = currentTimeMillis + ".jpg";

        StorageReference fileRef = imagesReference.child(fileName);
        UploadTask uploadTask = fileRef.putFile(uri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dialog.dismiss();

                        String downloadUrl = uri.toString();
                        imagePath = downloadUrl;
                        Glide.with(activity)
                                .load(downloadUrl)
                                .centerCrop()
                                .placeholder(R.drawable.placeholder)
                                .into(collectionBinding.ivCookCover);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if ((itemId == R.id.iProfile)) {
            NavDirections action =
                    MainFragmentDirections.
                            actionMainFragmentToProfileFragment();
            Navigation.findNavController(mBinding.getRoot()).navigate(action);
            mBinding.drawerLay.close();
        } else if ((itemId == R.id.iAboutUs)) {
            NavDirections action =
                    MainFragmentDirections.
                            actionMainFragmentToAboutUsFragment();
            Navigation.findNavController(mBinding.getRoot()).navigate(action);
            mBinding.drawerLay.close();
        } else if ((itemId == R.id.iContactUs)) {
            NavDirections action =
                    MainFragmentDirections.
                            actionMainFragmentToContactUsFragment();
            Navigation.findNavController(mBinding.getRoot()).navigate(action);
            mBinding.drawerLay.close();
        }
        else if ((itemId == R.id.iLogout)) {

            mAuth.signOut();

            NavDirections action =
                    MainFragmentDirections.
                            actionMainFragmentToSignInFragment();
            Navigation.findNavController(mBinding.getRoot()).navigate(action);
            mBinding.drawerLay.close();
        }
        return true;
    }

    @Override
    public void onReadClick(AllCookData cookData, boolean isChecked) {

        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();

        Map<String, Object> dataToUpdate = new HashMap<>();
        dataToUpdate.put("read", isChecked);

        DocumentReference docRef = db.collection("Cooks").document(cookData.getCookId());

        docRef.update(dataToUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Added", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onCurrentlyClick(AllCookData cookData, boolean b) {

        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();

        Map<String, Object> dataToUpdate = new HashMap<>();
        dataToUpdate.put("current", b);

        DocumentReference docRef = db.collection("Cooks").document(cookData.getCookId());

        docRef.update(dataToUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Added", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onFinishedClick(AllCookData cookData, boolean b) {
        Dialog dialog = Constant.getDialog(requireContext());
        dialog.show();

        Map<String, Object> dataToUpdate = new HashMap<>();
        dataToUpdate.put("finished", b);

        DocumentReference docRef = db.collection("cooks").document(cookData.getCookId());

        docRef.update(dataToUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Added", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onCookClick(AllCookData cookData) {
        Constant.allCookData = cookData;

        NavDirections action =
                MainFragmentDirections.
                        actionMainFragmentToCookDetailFragment();
        Navigation.findNavController(mBinding.getRoot()).navigate(action);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }
}