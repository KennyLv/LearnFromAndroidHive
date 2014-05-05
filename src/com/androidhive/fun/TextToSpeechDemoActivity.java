package com.androidhive.fun;

import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.androidhive.androidlearn.R;

public class TextToSpeechDemoActivity extends Activity implements TextToSpeech.OnInitListener {
	private String text = "I developed a simple interface with one input field and a button to trigger a event that will take text from input field and speaks out.";
	private TextView tx;
	private Button ttsBut;
	private TextToSpeech tts;
	private static final int REQ_TTS_STATUS_CHECK = 0;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_to_speech_demo);
		
		//检查TTS数据是否已经安装并且可用  
        Intent checkIntent = new Intent();  
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);  
        startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);  
		
		//tts = new TextToSpeech(this, this);
		tx = (TextView) this.findViewById(R.id.speech_text);
		tx.setText(text);
		
		ttsBut = (Button) this.findViewById(R.id.button_tts);
		ttsBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
                speakOut();
			}
		});
		
	}

	@Override
	public void onInit(int status) {
		Log.d("-=-=-=-=-=-", "onInit....");	
		if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", String.valueOf(result) + "This Language is not supported");
            	ttsBut.setEnabled(false);
            } else {
            	ttsBut.setEnabled(true);
                speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
	}

	 @Override  
    protected void onPause() {  
        // TODO Auto-generated method stub  
        super.onPause();  
        if(tts != null)  
            //activity暂停时也停止TTS  
        {  
            tts.stop();  
        }  
    }  
	
	@Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
	 
	private void speakOut() {
		String text = tx.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
	
	
	protected  void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if(requestCode == REQ_TTS_STATUS_CHECK)  
        {  
            switch (resultCode) {  
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS: //这个返回结果表明TTS Engine可以用  
	            {  
	                tts = new TextToSpeech(this, this);  
	                Log.v("-=-=-=-=-=-=-", "TTS Engine is installed!");  
	                  
	            }
                break;  
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA: //需要的语音数据已损坏  
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA: //缺少需要语言的语音数据  
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME: //缺少需要语言的发音数据  
	            {  
	                //这三种情况都表明数据有错,重新下载安装需要的数据  
	                Log.v("-=-=-=-=-=-=-", "Need language stuff:"+resultCode); //-2
	                Intent dataIntent = new Intent();  
	                dataIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);  
	                startActivity(dataIntent);  
	            }
                break;  
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL: //检查失败  
	            default:  
	                Log.v("-=-=-=-=-=-=-", "Got a failure. TTS apparently not available");  
                break;
            } 
        }else{  
            //其他Intent返回的结果  
        }  
    }
}
