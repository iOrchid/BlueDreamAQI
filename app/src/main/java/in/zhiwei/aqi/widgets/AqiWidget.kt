package `in`.zhiwei.aqi.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.util.Log
import android.widget.RemoteViews

import `in`.zhiwei.aqi.R

/**
 * widget，就是一个receiver，实现update、delete等函数的复写
 */
class AqiWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        updateAppWidget(context, appWidgetManager)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            Log.i("AqiWidget", "appWidgetId:$appWidgetId")
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        val WIDGET_REQUEST_CODE = 101//请求码

        /**
         * 静态函数，更新widget
         *
         * @param context          context
         * @param appWidgetManager widgetManager
         */
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager) {

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.aqi_widget)
            views.setTextViewText(R.id.tv_aqi_widget, "105")
            views.setTextViewText(R.id.tv_city_widget, "北京")
            // 更新widget
            val componentName = ComponentName(context, AqiWidget::class.java)
            appWidgetManager.updateAppWidget(componentName, views)
            //		appWidgetManager.updateAppWidget(id,views);
        }
    }
}

