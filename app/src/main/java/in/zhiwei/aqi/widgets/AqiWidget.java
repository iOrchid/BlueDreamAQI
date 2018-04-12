package in.zhiwei.aqi.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import in.zhiwei.aqi.R;

/**
 * widget，就是一个receiver，实现update、delete等函数的复写
 */
public class AqiWidget extends AppWidgetProvider {

	public static final int WIDGET_REQUEST_CODE = 101;//请求码

	/**
	 * 静态函数，更新widget
	 *
	 * @param context          context
	 * @param appWidgetManager widgetManager
	 */
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager) {

		// Construct the RemoteViews object
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.aqi_widget);
		views.setTextViewText(R.id.tv_aqi_widget, "105");
		views.setTextViewText(R.id.tv_city_widget, "北京");
		// 更新widget
		ComponentName componentName = new ComponentName(context, AqiWidget.class);
		appWidgetManager.updateAppWidget(componentName, views);
//		appWidgetManager.updateAppWidget(id,views);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		updateAppWidget(context, appWidgetManager);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// When the user deletes the widget, delete the preference associated with it.
		for (int appWidgetId : appWidgetIds) {
			Log.i("AqiWidget", "appWidgetId:" + appWidgetId);
		}
	}

	@Override
	public void onEnabled(Context context) {
		// Enter relevant functionality for when the first widget is created
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}
}

