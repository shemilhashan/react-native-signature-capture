package com.rssignaturecapture;

import android.content.Context;
import android.util.Log;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.rssignaturecapture.RSSignatureCaptureContextModule;

import java.util.Map;

import javax.annotation.Nullable;

public class RSSignatureCaptureViewManager extends ViewGroupManager<RSSignatureCaptureMainView> {

	public static final String PROPS_SAVE_IMAGE_FILE="saveImageFileInExtStorage";
	public static final String PROPS_VIEW_MODE = "viewMode";
	public static final String PROPS_SHOW_NATIVE_BUTTONS="showNativeButtons";
	public static final String PROPS_MAX_SIZE="maxSize";
	public static final String PROPS_IMAGE_SAVE_PATH="savePath";
	public static final String PROPS_IMAGE_ID="imageId";
	public static final int COMMAND_SAVE_IMAGE = 1;
	public static final int COMMAND_RESET_IMAGE = 2;
	public Context reactContext = null;

	private RSSignatureCaptureContextModule mContextModule;

	public RSSignatureCaptureViewManager(ReactApplicationContext reactContext) {
		mContextModule = new RSSignatureCaptureContextModule(reactContext);
		this.reactContext = reactContext;
	}

	@Override
	public String getName() {
		return "RSSignatureView";
	}

	@ReactProp(name = PROPS_SAVE_IMAGE_FILE)
	public void setSaveImageFileInExtStorage(RSSignatureCaptureMainView view, @Nullable Boolean saveFile) {
		Log.d("setFileInExtStorage:", "" + saveFile);
		if(view!=null){
			view.setSaveFileInExtStorage(saveFile);
		}
	}

	@ReactProp(name = PROPS_VIEW_MODE)
	public void setViewMode(RSSignatureCaptureMainView view, @Nullable String viewMode) {
		Log.d("setViewMode:", "" + viewMode);
		if(view!=null){
			view.setViewMode(viewMode);
		}
	}


	@ReactProp(name = PROPS_SHOW_NATIVE_BUTTONS)
	public void setPropsShowNativeButtons(RSSignatureCaptureMainView view, @Nullable Boolean showNativeButtons) {
		Log.d("showNativeButtons:", "" + showNativeButtons);
		if(view!=null){
			view.setShowNativeButtons(showNativeButtons);
		}
	}


	@ReactProp(name = PROPS_MAX_SIZE)
	public void setPropsWidth(RSSignatureCaptureMainView view, @Nullable Integer maxSize) {
		Log.d("maxSize:",  ""+maxSize);
		if(view!=null){
			view.setMaxSize(maxSize);
		}
	}

	@ReactProp(name = PROPS_IMAGE_SAVE_PATH)
	public void setSavePath(RSSignatureCaptureMainView view, @Nullable String savePath) {
		Log.d("setSavePath:",  ""+savePath);
		if(view!=null){
			if(savePath.equals("temp")){
				savePath = this.reactContext.getCacheDir().getAbsolutePath();
			}

			view.setSavePath(savePath);
		}
	}

	@ReactProp(name = PROPS_IMAGE_ID)
	public void setImageId(RSSignatureCaptureMainView view, @Nullable String imageId) {
		Log.d("setImageId:",  ""+imageId);
		if(view!=null){
			view.setImageId(imageId);
		}
	}



	@Override
	public RSSignatureCaptureMainView createViewInstance(ThemedReactContext context) {
		Log.d("React"," View manager createViewInstance:");
		return new RSSignatureCaptureMainView(context, mContextModule.getActivity());
	}

	@Override
	public Map<String,Integer> getCommandsMap() {
		Log.d("React"," View manager getCommandsMap:");
		return MapBuilder.of(
				"saveImage",
				COMMAND_SAVE_IMAGE,
				"resetImage",
				COMMAND_RESET_IMAGE);
	}

	@Override
	public void receiveCommand(
			RSSignatureCaptureMainView view,
			int commandType,
			@Nullable ReadableArray args) {
		Assertions.assertNotNull(view);
		Assertions.assertNotNull(args);
		switch (commandType) {
			case COMMAND_SAVE_IMAGE: {
				view.saveImage();
				return;
			}
			case COMMAND_RESET_IMAGE: {
				view.reset();
				return;
			}

			default:
				throw new IllegalArgumentException(String.format(
						"Unsupported command %d received by %s.",
						commandType,
						getClass().getSimpleName()));
		}
	}


}
