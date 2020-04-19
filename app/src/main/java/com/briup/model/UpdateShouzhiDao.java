package com.briup.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UpdateShouzhiDao {

	//Edit details
	public static void updateRecord(Context context,String nowUser,
			float newMoney,String newShouzhi,String newDate,
			String newType,String newQianbao,String newBeizhu,
			float oldMoney,String oldShouzhi,String oldDate,
			String oldType,String oldQianbao,String oldBeizhu){
		
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getWritableDB(openHelper);
		
		String sql="update "+nowUser+"_record set shouzhi='"+newShouzhi+"',money="+newMoney
				+",type='"+newType+"',qianbao='"+newQianbao+"',date='"+newDate+"',beizhu='"+newBeizhu+"'"
				+" where shouzhi='"+oldShouzhi+"' and money="+oldMoney+" and type='"+oldType
				+"' and qianbao='"+oldQianbao+"' and date='"+oldDate+"' and beizhu='"+oldBeizhu+"'";

		db.execSQL(sql);
	}
	
	//Modify the total amount when modifying the details
	public static void updateUAmountMoney(Context context,String userName,
			String oldShouzhiStr,String oldQianbaoStr,float oldMoney,
			String newShouzhiStr,String newQianbaoStr,float newMoney){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getWritableDB(openHelper);
		Cursor cursor=db.query("account_users", new String[]{"xianjin","chuxuka","xinyongka","zhifubao"},
				"name=?", new String[]{userName}, null, null, null);
		float xianjin=0;
		float chuxuka=0;
		float xinyongka=0;
		float zhifubao=0;
		
		float result1=0.0f;
		float result2=0.0f;
				
		while(cursor.moveToNext()){
			xianjin=cursor.getFloat(cursor.getColumnIndex("xianjin"));
			chuxuka=cursor.getFloat(cursor.getColumnIndex("chuxuka"));
			xinyongka=cursor.getFloat(cursor.getColumnIndex("xinyongka"));
			zhifubao=cursor.getFloat(cursor.getColumnIndex("zhifubao"));
	    }
		
		ContentValues values=new ContentValues();
		
		if("Cash".equals(oldQianbaoStr) && "Cash".equals(newQianbaoStr)){
			if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xianjin-oldMoney+newMoney;
				values.put("xianjin", result1);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xianjin-oldMoney-newMoney;
				values.put("xianjin", result1);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xianjin+oldMoney+newMoney;
				values.put("xianjin", result1);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xianjin+oldMoney-newMoney;
				values.put("xianjin", result1);
			}
		}else if("Cash".equals(oldQianbaoStr) && "Debit Card".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=xianjin-oldMoney;
	        	result2=chuxuka+newMoney;
	        	values.put("xianjin", result1);
	        	values.put("chuxuka", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xianjin-oldMoney;
				result2=chuxuka-newMoney;
				values.put("xianjin", result1);
	        	values.put("chuxuka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xianjin+oldMoney;
				result2=chuxuka+newMoney;
				values.put("xianjin", result1);
	        	values.put("chuxuka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xianjin+oldMoney;
				result2=chuxuka-newMoney;
				values.put("xianjin", result1);
	        	values.put("chuxuka", result2);
			}
		}else if("Cash".equals(oldQianbaoStr) && "Credit Card".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=xianjin-oldMoney;
	        	result2=xinyongka+newMoney;
	        	values.put("xianjin", result1);
	        	values.put("xinyongka", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xianjin-oldMoney;
				result2=xinyongka-newMoney;
				values.put("xianjin", result1);
	        	values.put("xinyongka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xianjin+oldMoney;
				result2=xinyongka+newMoney;
				values.put("xianjin", result1);
	        	values.put("xinyongka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xianjin+oldMoney;
				result2=xinyongka-newMoney;
				values.put("xianjin", result1);
	        	values.put("xinyongka", result2);
			}
		}else if("Cash".equals(oldQianbaoStr) && "PayPal".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=xianjin-oldMoney;
	        	result2=zhifubao+newMoney;
	        	values.put("xianjin", result1);
	        	values.put("zhifubao", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xianjin-oldMoney;
				result2=zhifubao-newMoney;
				values.put("xianjin", result1);
	        	values.put("zhifubao", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xianjin+oldMoney;
				result2=zhifubao+newMoney;
				values.put("xianjin", result1);
	        	values.put("zhifubao", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xianjin+oldMoney;
				result2=zhifubao-newMoney;
				values.put("xianjin", result1);
	        	values.put("zhifubao", result2);
			}
		}
		
		
		else if("Debit Card".equals(oldQianbaoStr) && "Debit Card".equals(newQianbaoStr)){
			if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=chuxuka-oldMoney+newMoney;
				values.put("chuxuka", result1);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=chuxuka-oldMoney-newMoney;
				values.put("chuxuka", result1);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=chuxuka+oldMoney+newMoney;
				values.put("chuxuka", result1);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=chuxuka+oldMoney-newMoney;
				values.put("chuxuka", result1);
			}
		}else if("Debit Card".equals(oldQianbaoStr) && "Cash".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=chuxuka-oldMoney;
	        	result2=xianjin+newMoney;
	        	values.put("chuxuka", result1);
	        	values.put("xianjin", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=chuxuka-oldMoney;
				result2=xianjin-newMoney;
				values.put("chuxuka", result1);
	        	values.put("xianjin", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=chuxuka+oldMoney;
				result2=xianjin+newMoney;
				values.put("chuxuka", result1);
	        	values.put("xianjin", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=chuxuka+oldMoney;
				result2=xianjin-newMoney;
				values.put("chuxuka", result1);
	        	values.put("xianjin", result2);
			}
		}else if("Debit Card".equals(oldQianbaoStr) && "Credit Card".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=chuxuka-oldMoney;
	        	result2=xinyongka+newMoney;
	        	values.put("chuxuka", result1);
	        	values.put("xinyongka", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=chuxuka-oldMoney;
				result2=xinyongka-newMoney;
				values.put("chuxuka", result1);
	        	values.put("xinyongka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=chuxuka+oldMoney;
				result2=xinyongka+newMoney;
				values.put("chuxuka", result1);
	        	values.put("xinyongka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=chuxuka+oldMoney;
				result2=xinyongka-newMoney;
				values.put("chuxuka", result1);
	        	values.put("xinyongka", result2);
			}
		}else if("Debit Card".equals(oldQianbaoStr) && "PayPal".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=chuxuka-oldMoney;
	        	result2=zhifubao+newMoney;
	        	values.put("chuxuka", result1);
	        	values.put("zhifubao", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=chuxuka-oldMoney;
				result2=zhifubao-newMoney;
				values.put("chuxuka", result1);
	        	values.put("zhifubao", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=chuxuka+oldMoney;
				result2=zhifubao+newMoney;
				values.put("chuxuka", result1);
	        	values.put("zhifubao", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=chuxuka+oldMoney;
				result2=zhifubao-newMoney;
				values.put("chuxuka", result1);
	        	values.put("zhifubao", result2);
			}
		}
		
		
		else if("Credit Card".equals(oldQianbaoStr) && "Credit Card".equals(newQianbaoStr)){
			if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xinyongka-oldMoney+newMoney;
				values.put("xinyongka", result1);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xinyongka-oldMoney-newMoney;
				values.put("xinyongka", result1);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xinyongka+oldMoney+newMoney;
				values.put("xinyongka", result1);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xinyongka+oldMoney-newMoney;
				values.put("xinyongka", result1);
			}
		}else if("Credit Card".equals(oldQianbaoStr) && "Cash".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=xinyongka-oldMoney;
	        	result2=xianjin+newMoney;
	        	values.put("xinyongka", result1);
	        	values.put("xianjin", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xinyongka-oldMoney;
				result2=xianjin-newMoney;
				values.put("xinyongka", result1);
	        	values.put("xianjin", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xinyongka+oldMoney;
				result2=xianjin+newMoney;
				values.put("xinyongka", result1);
	        	values.put("xianjin", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xinyongka+oldMoney;
				result2=xianjin-newMoney;
				values.put("xinyongka", result1);
	        	values.put("xianjin", result2);
			}
		}else if("Credit Card".equals(oldQianbaoStr) && "Debit Card".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=xinyongka-oldMoney;
	        	result2=chuxuka+newMoney;
	        	values.put("xinyongka", result1);
	        	values.put("chuxuka", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xinyongka-oldMoney;
				result2=chuxuka-newMoney;
				values.put("xinyongka", result1);
	        	values.put("chuxuka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xinyongka+oldMoney;
				result2=chuxuka+newMoney;
				values.put("xinyongka", result1);
	        	values.put("chuxuka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xinyongka+oldMoney;
				result2=chuxuka-newMoney;
				values.put("xinyongka", result1);
	        	values.put("chuxuka", result2);
			}
		}else if("Credit Card".equals(oldQianbaoStr) && "PayPal".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=xinyongka-oldMoney;
	        	result2=zhifubao+newMoney;
	        	values.put("xinyongka", result1);
	        	values.put("zhifubao", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xinyongka-oldMoney;
				result2=zhifubao-newMoney;
				values.put("xinyongka", result1);
	        	values.put("zhifubao", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=xinyongka+oldMoney;
				result2=zhifubao+newMoney;
				values.put("xinyongka", result1);
	        	values.put("zhifubao", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=xinyongka+oldMoney;
				result2=zhifubao-newMoney;
				values.put("xinyongka", result1);
	        	values.put("zhifubao", result2);
			}
		}
		
		
		else if("PayPal".equals(oldQianbaoStr) && "PayPal".equals(newQianbaoStr)){
			if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=zhifubao-oldMoney+newMoney;
				values.put("zhifubao", result1);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=zhifubao-oldMoney-newMoney;
				values.put("zhifubao", result1);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=zhifubao+oldMoney+newMoney;
				values.put("zhifubao", result1);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=zhifubao+oldMoney-newMoney;
				values.put("zhifubao", result1);
			}
		}else if("PayPal".equals(oldQianbaoStr) && "Cash".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=zhifubao-oldMoney;
	        	result2=xianjin+newMoney;
	        	values.put("zhifubao", result1);
	        	values.put("xianjin", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=zhifubao-oldMoney;
				result2=xianjin-newMoney;
				values.put("zhifubao", result1);
	        	values.put("xianjin", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=zhifubao+oldMoney;
				result2=xianjin+newMoney;
				values.put("zhifubao", result1);
	        	values.put("xianjin", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=zhifubao+oldMoney;
				result2=xianjin-newMoney;
				values.put("zhifubao", result1);
	        	values.put("xianjin", result2);
			}
		}else if("PayPal".equals(oldQianbaoStr) && "Debit Card".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=zhifubao-oldMoney;
	        	result2=chuxuka+newMoney;
	        	values.put("zhifubao", result1);
	        	values.put("chuxuka", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=zhifubao-oldMoney;
				result2=chuxuka-newMoney;
				values.put("zhifubao", result1);
	        	values.put("chuxuka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=zhifubao+oldMoney;
				result2=chuxuka+newMoney;
				values.put("zhifubao", result1);
	        	values.put("chuxuka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=zhifubao+oldMoney;
				result2=chuxuka-newMoney;
				values.put("zhifubao", result1);
	        	values.put("chuxuka", result2);
			}
		}else if("PayPal".equals(oldQianbaoStr) && "Credit Card".equals(newQianbaoStr)){
	        if("Income".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
	        	result1=zhifubao-oldMoney;
	        	result2=xinyongka+newMoney;
	        	values.put("zhifubao", result1);
	        	values.put("xinyongka", result2);
			}else if("Income".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=zhifubao-oldMoney;
				result2=xinyongka-newMoney;
				values.put("zhifubao", result1);
	        	values.put("xinyongka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Income".equals(newShouzhiStr)){
				result1=zhifubao+oldMoney;
				result2=xinyongka+newMoney;
				values.put("zhifubao", result1);
	        	values.put("xinyongka", result2);
			}else if("Expenditure".equals(oldShouzhiStr) && "Expenditure".equals(newShouzhiStr)){
				result1=zhifubao+oldMoney;
				result2=xinyongka-newMoney;
				values.put("zhifubao", result1);
	        	values.put("xinyongka", result2);
			}
		}
		
	    
/*		if(result1>=0 && result2>=0){
			db.update("account_users", values, "name=?", new String[]{userName});
			return true;
		}else{
			return false;
		}*/
		db.update("account_users", values, "name=?", new String[]{userName});
	}
}
