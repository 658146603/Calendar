package tools;

import com.qscftyjm.calendar.LoginActivity;
import com.qscftyjm.calendar.RegisterActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

public class AlertDialogUtil {

	public static void makeRegisterResultDialog(final Context context, String account, String username){
		
        final TextView ac = new TextView(context);
        ac.setHeight(120);
        ac.setLines(2);
        ac.setText("你的账号为 ： "+account+"\n后续登录账号仅以该账号和密码登录有效");
        new AlertDialog.Builder(context).setTitle("注册成功，欢迎 "+username+" ！")
                .setView(ac)
                .setPositiveButton("跳转登录界面", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    	Intent intent=new Intent(context, LoginActivity.class);
                    	context.startActivity(intent);
                    	((Activity) context).finish();
        		    }
                }).show();
                    
	}
}
