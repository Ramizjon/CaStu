package ramiz.com.castu.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import ramiz.com.castu.R;
import ramiz.com.castu.controller.DataProcessor;
import ramiz.com.castu.controller.StupidityGenerator;
import ramiz.com.castu.model.Stupidity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddStupidityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddStupidityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStupidityFragment extends Fragment implements PopupMenu.OnMenuItemClickListener, CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static int PICK_IMAGE = 1;
    private final static int CAPTURE_IMAGE = 2;
    private final static int GET_LOCATION = 3;

    String photoPath;
    String selectedImagePath;
    String longitude, latitude;
    LocationManager locationManager;
    LocationListener locationListener;
    boolean isSelectedCurrentLocation = false;
    boolean isGPSEnabled;

    DataProcessor dataProcessor;
    ScrollView scrollView;
    RadioButton rb1, rb2, rb3;
    ImageView cameraImageView;
    Button addPhotoButton;
    Button generateButton;
    Button addCurrentLocationButton;
    Button selectLocationButton;
    Bitmap attachedPhoto;
    EditText commentEditText;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4,
            checkBox5, checkBox6, checkBox7, checkBox8, checkBox9;

    ArrayList<CheckBox> checkBoxes;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddStupidityFragment() {
        // Required empty public constructor
    }


    public static AddStupidityFragment newInstance(String param1, String param2) {
        AddStupidityFragment fragment = new AddStupidityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getFragmentManager().putFragment(savedInstanceState, "stupidityfragment", this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isSelectedCurrentLocation) {
                latitude = String.valueOf(loc.getLatitude());
                longitude = String.valueOf(loc.getLongitude());
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_stupidity, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        checkBoxes = new ArrayList<CheckBox>();
        cameraImageView = (ImageView) view.findViewById(R.id.cameraImageView);
        addPhotoButton = (Button) view.findViewById(R.id.addPhotoButton);
        addPhotoButton.setOnClickListener(addPhotoOnClickListener);
        generateButton = (Button) view.findViewById(R.id.shareStupidityButton);
        generateButton.setOnClickListener(generateStupidityOnClickListener);
        addCurrentLocationButton = (Button) view.findViewById(R.id.addCurrentLocationButton);
        selectLocationButton = (Button) view.findViewById(R.id.selectLocationButton);
        selectLocationButton.setOnClickListener(this.selectLocationButtonOnClickListener);
        addCurrentLocationButton.setOnClickListener(addCurrentLocationButtonOnClickListener);
        rb1 = (RadioButton) view.findViewById(R.id.radiobutton1);
        rb2 = (RadioButton) view.findViewById(R.id.radiobutton2);
        rb3 = (RadioButton) view.findViewById(R.id.radiobutton3);
        rb1.setOnCheckedChangeListener(this);
        rb2.setOnCheckedChangeListener(this);
        rb3.setOnCheckedChangeListener(this);
        commentEditText = (EditText) view.findViewById(R.id.editText);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.
                    requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        checkBoxes.add(checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1));
        checkBoxes.add(checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2));
        checkBoxes.add(checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3));
        checkBoxes.add(checkBox4 = (CheckBox) view.findViewById(R.id.checkBox4));
        checkBoxes.add(checkBox5 = (CheckBox) view.findViewById(R.id.checkBox5));
        checkBoxes.add(checkBox6 = (CheckBox) view.findViewById(R.id.checkBox6));
        checkBoxes.add(checkBox7 = (CheckBox) view.findViewById(R.id.checkBox7));
        checkBoxes.add(checkBox8 = (CheckBox) view.findViewById(R.id.checkBox8));
        checkBoxes.add(checkBox9 = (CheckBox) view.findViewById(R.id.checkBox9));
        return view;
    }


    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.radiobutton1) {
                rb2.setChecked(false);
                rb3.setChecked(false);
            }
            if (buttonView.getId() == R.id.radiobutton2) {
                rb1.setChecked(false);
                rb3.setChecked(false);
            }
            if (buttonView.getId() == R.id.radiobutton3) {
                rb2.setChecked(false);
                rb1.setChecked(false);
            }
        }
    }

    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.item_camera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                startActivityForResult(intent, CAPTURE_IMAGE);
                return true;
            case R.id.item_gallery:
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
                return true;
        }
        return true;
    }


    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".jpg");
        Uri imgUri = Uri.fromFile(file);
        this.photoPath = file.getAbsolutePath();
        return imgUri;
    }

    public String getImagePath() {
        return photoPath;
    }

    public String getRealPathFromURI(Bitmap attachedPhoto) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        attachedPhoto.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), attachedPhoto, "Title", null);
        Uri uri = Uri.parse(path);
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    View.OnClickListener addPhotoOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                    popupMenu.setOnMenuItemClickListener(AddStupidityFragment.this);
                    popupMenu.inflate(R.menu.popup_photo_menu);
                    popupMenu.show();
                }
            };

    View.OnClickListener generateStupidityOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((latitude != null) && (longitude != null)) {
                        ArrayList<Boolean> booleanArrayList = new ArrayList<Boolean>();
                        for (CheckBox c : checkBoxes) {
                            booleanArrayList.add(c.isChecked());
                        }
                        StupidityGenerator sg = new StupidityGenerator();
                        sg.parseTypes(booleanArrayList);
                        if (rb1.isChecked()) sg.parseCategory(0);
                        else if (rb2.isChecked()) sg.parseCategory(1);
                        else sg.parseCategory(2);
                        sg.setComment(commentEditText.getText().toString());
                        sg.setDate();
                        sg.setLongitude(Float.parseFloat(longitude));
                        sg.setLatitude(Float.parseFloat(latitude));
                        Stupidity stup = sg.generateStupidity();
                        Toast.makeText(getActivity(), "Network error 503: Bad gateway", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getActivity(), stup.toJSON(), Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), "Местоположение не прикреплено", Toast.LENGTH_SHORT).show();

                }
            };

    View.OnClickListener addCurrentLocationButtonOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isGPSEnabled) {
                        isSelectedCurrentLocation = true;
                        generateButton.setBackgroundColor(Color.parseColor("#10CC8A"));
                    } else
                        Toast.makeText(getActivity(), "GPS не активирован", Toast.LENGTH_SHORT).show();
                }
            };


    View.OnClickListener selectLocationButtonOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   isSelectedCurrentLocation = false;
                   Intent intent = new Intent(AddStupidityFragment.this.getContext(), LocationPickerMapActivity.class);
                   startActivityForResult(intent, GET_LOCATION);
                }
            };


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case PICK_IMAGE:
                    selectedImagePath = getAbsolutePath(data.getData());
                    cameraImageView.setVisibility(View.VISIBLE);
                    attachedPhoto = decodeFile(selectedImagePath);
                    attachedPhoto = Bitmap.createScaledBitmap(attachedPhoto, 950, 950, false);
                    cameraImageView.setImageBitmap(attachedPhoto);
                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(selectedImagePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case CAPTURE_IMAGE:
                    selectedImagePath = getImagePath();
                    cameraImageView.setVisibility(View.VISIBLE);
                    attachedPhoto = decodeFile(selectedImagePath);
                    attachedPhoto = Bitmap.createScaledBitmap(attachedPhoto, 950, 950, false);
                    cameraImageView.setImageBitmap(attachedPhoto);
                    break;


                case GET_LOCATION:
                    latitude = data.getStringExtra("latitude");
                    longitude = data.getStringExtra("longitude");
                    generateButton.setBackgroundColor(Color.parseColor("#10CC8A"));
                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);

            }
        }

    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 400;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
