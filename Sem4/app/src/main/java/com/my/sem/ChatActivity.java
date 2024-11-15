package com.my.sem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Button;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.content.Intent;
import android.content.ClipData;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Continuation;
import android.net.Uri;
import java.io.File;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.provider.MediaStore;
import android.os.Build;
import androidx.core.content.FileProvider;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import java.text.DecimalFormat;
import com.bumptech.glide.Glide;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class ChatActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	public final int REQ_CD_CAM = 102;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private Toolbar _toolbar;
	private HashMap<String, Object> chatm = new HashMap<>();
	private double i = 0;
	private String fio = "";
	private String idprof = "";
	private HashMap<String, Object> profile = new HashMap<>();
	private double mykeyid = 0;
	private String pos = "";
	private String path = "";
	private double nimg = 0;
	private String url = "";
	private String pname = "";
	private boolean isUpload = false;
	private boolean isClick = false;
	private String idchat = "";
	private HashMap<String, Object> mymessage = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> chats = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> profileList = new ArrayList<>();
	private ArrayList<String> images = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> mychat = new ArrayList<>();
	
	private LinearLayout linear3;
	private LinearLayout linear2;
	private EditText message;
	private LinearLayout linear5;
	private ListView listview1;
	private ImageView addimg;
	private Button sendmessage;
	
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private Calendar time = Calendar.getInstance();
	private LocationManager location;
	private LocationListener _location_location_listener;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference picture = _firebase_storage.getReference("data/pictures");
	private OnCompleteListener<Uri> _picture_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _picture_download_success_listener;
	private OnSuccessListener _picture_delete_success_listener;
	private OnProgressListener _picture_upload_progress_listener;
	private OnProgressListener _picture_download_progress_listener;
	private OnFailureListener _picture_failure_listener;
	private DatabaseReference userProfile = _firebase.getReference("data/profiles");
	private ChildEventListener _userProfile_child_listener;
	private DatabaseReference chat = _firebase.getReference("data/chat");
	private ChildEventListener _chat_child_listener;
	private DatabaseReference work = _firebase.getReference("data/workchat");
	private ChildEventListener _work_child_listener;
	private Intent view = new Intent();
	private Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_cam;
	private AlertDialog.Builder dial;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chat);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		message = (EditText) findViewById(R.id.message);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		listview1 = (ListView) findViewById(R.id.listview1);
		addimg = (ImageView) findViewById(R.id.addimg);
		sendmessage = (Button) findViewById(R.id.sendmessage);
		auth = FirebaseAuth.getInstance();
		location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		_file_cam = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_cam = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_cam= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_cam);
		}
		else {
			_uri_cam = Uri.fromFile(_file_cam);
		}
		cam.putExtra(MediaStore.EXTRA_OUTPUT, _uri_cam);
		cam.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		dial = new AlertDialog.Builder(this);
		
		addimg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dial.setMessage("Загрузить фото");
				dial.setPositiveButton("Сделать снимок", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						startActivityForResult(cam, REQ_CD_CAM);
					}
				});
				dial.setNeutralButton("Загрузить с телефона", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						startActivityForResult(fp, REQ_CD_FP);
					}
				});
				dial.create().show();
			}
		});
		
		sendmessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (message.getText().toString().equals("") && (nimg == 0)) {
					
				}
				else {
					chatm = new HashMap<>();
					chatm.put("idchat", idchat);
					chatm.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
					chatm.put("name", fio);
					if (!message.getText().toString().equals("")) {
						chatm.put("message", message.getText().toString());
					}
					chatm.put("time", new SimpleDateFormat("HH:mm  dd.MM.yy").format(time.getTime()));
					chatm.put("position", pos);
					message.setText("");
					if (nimg == 0) {
						chat.push().updateChildren(chatm);
					}
					else {
						picture.child(pname).putFile(Uri.fromFile(new File(path))).addOnFailureListener(_picture_failure_listener).addOnProgressListener(_picture_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
							@Override
							public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
								return picture.child(pname).getDownloadUrl();
							}}).addOnCompleteListener(_picture_upload_success_listener);
						nimg = 0;
					}
				}
			}
		});
		
		_location_location_listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location _param1) {
				final double _lat = _param1.getLatitude();
				final double _lng = _param1.getLongitude();
				final double _acc = _param1.getAccuracy();
				pos = String.valueOf(_lat).concat(":".concat(String.valueOf(_lng)));
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onProviderDisabled(String provider) {}
		};
		
		_picture_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				if (_progressValue == 100) {
					isUpload = true;
				}
			}
		};
		
		_picture_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_picture_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				url = _downloadUrl;
				if (isUpload) {
					chatm.put("url", url);
				}
				chat.push().updateChildren(chatm);
			}
		};
		
		_picture_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_picture_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_picture_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_userProfile_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		userProfile.addChildEventListener(_userProfile_child_listener);
		
		_chat_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (idchat.equals(_childValue.get("idchat").toString())) {
					mychat.add(_childValue);
					listview1.setAdapter(new Listview1Adapter(mychat));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		chat.addChildEventListener(_chat_child_listener);
		
		_work_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		work.addChildEventListener(_work_child_listener);
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	private void initializeLogic() {
		work.addChildEventListener(_work_child_listener);
		idchat = getIntent().getStringExtra("idchat");
		profile = new HashMap<>();
		idprof = FirebaseAuth.getInstance().getCurrentUser().getUid();
		userProfile.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				profileList = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						profileList.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				i = 0;
				for(int _repeat15 = 0; _repeat15 < (int)(profileList.size()); _repeat15++) {
					if (idprof.equals(profileList.get((int)i).get("id").toString())) {
						profile = profileList.get((int)i);
						mykeyid = i;
						fio = profile.get("sname").toString().concat(" ".concat(profile.get("name").toString().substring((int)(0), (int)(1)).concat(".".concat(profile.get("otch").toString().substring((int)(0), (int)(1)).concat(".")))));
					}
					i++;
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		mymessage = new HashMap<>();
		chat.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				chats = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						chats.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				listview1.setAdapter(new Listview1Adapter(mychat));
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, _location_location_listener);
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				isUpload = false;
				chatm.put("img", _filePath.get((int)(0)));
				nimg++;
				path = _filePath.get((int)(0));
				pname = Uri.parse(_filePath.get((int)(0))).getLastPathSegment();
			}
			else {
				
			}
			break;
			
			case REQ_CD_CAM:
			if (_resultCode == Activity.RESULT_OK) {
				 String _filePath = _file_cam.getAbsolutePath();
				
				isUpload = false;
				chatm.put("img", _filePath);
				nimg++;
				path = _filePath;
				pname = Uri.parse(_filePath).getLastPathSegment();
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		view.setClass(getApplicationContext(), ChatsActivity.class);
		view.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(view);
	}
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.post, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView name = (TextView) _v.findViewById(R.id.name);
			final TextView messagep = (TextView) _v.findViewById(R.id.messagep);
			final ImageView img = (ImageView) _v.findViewById(R.id.img);
			final TextView time = (TextView) _v.findViewById(R.id.time);
			
			if (mychat.get((int)_position).get("idchat").toString().equals(idchat)) {
				name.setText(mychat.get((int)_position).get("name").toString());
				if (mychat.get((int)_position).containsKey("message")) {
					messagep.setVisibility(View.VISIBLE);
					messagep.setText(mychat.get((int)_position).get("message").toString());
				}
				else {
					messagep.setVisibility(View.GONE);
				}
				time.setText(mychat.get((int)_position).get("time").toString());
				if (mychat.get((int)_position).containsKey("url")) {
					img.setVisibility(View.VISIBLE);
					Glide.with(getApplicationContext()).load(Uri.parse(mychat.get((int)_position).get("url").toString())).into(img);
				}
				else {
					img.setVisibility(View.GONE);
				}
			}
			
			return _v;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
