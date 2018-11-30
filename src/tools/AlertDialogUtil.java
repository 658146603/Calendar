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
        ac.setText("����˺�Ϊ �� "+account+"\n������¼�˺Ž��Ը��˺ź������¼��Ч");
        new AlertDialog.Builder(context).setTitle("ע��ɹ�����ӭ "+username+" ��")
                .setView(ac)
                .setPositiveButton("��ת��¼����", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    	Intent intent=new Intent(context, LoginActivity.class);
                    	context.startActivity(intent);
                    	((Activity) context).finish();
        		    }
                }).show();
                    
	}
}
