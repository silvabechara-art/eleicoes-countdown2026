package br.com.eleicoes.contagem

import android.app.*
import android.content.*
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : Activity() {
    private val target = Calendar.getInstance().apply { set(2026, Calendar.OCTOBER, 4, 8, 0, 0); set(Calendar.MILLISECOND,0) }
    private val fmt = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var days: TextView; private lateinit var hms: TextView; private lateinit var clock: TextView
    override fun onCreate(b: Bundle?) { super.onCreate(b); setContentView(R.layout.activity_main)
        days=findViewById(R.id.days); hms=findViewById(R.id.hms); clock=findViewById(R.id.clock)
        findViewById<Button>(R.id.settings).setOnClickListener { AlarmScheduler.openAlarmPermission(this) }
        NotificationHelper.createChannel(this)
        requestNotificationPermission()
        AlarmScheduler.schedule(this)
        tick()
    }
    override fun onResume() {
        super.onResume()
        AlarmScheduler.schedule(this)
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission("android.permission.POST_NOTIFICATIONS") != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf("android.permission.POST_NOTIFICATIONS"), 100)
        }
    }

    private fun tick(){ val now=System.currentTimeMillis(); val d=target.timeInMillis-now; if(d<=0){days.text="0";hms.text="00 : 00 : 00"} else {days.text=(d/86400000).toString().padStart(3,'0'); val s=d/1000%60; val m=d/60000%60; val h=d/3600000%24; hms.text=String.format(Locale.getDefault(),"%02d : %02d : %02d",h,m,s)}; clock.text=fmt.format(Date(now)); handler.postDelayed({tick()},1000)}
}

object AlarmScheduler {
    fun schedule(c: Context){ val am=c.getSystemService(AlarmManager::class.java); if(Build.VERSION.SDK_INT>=31 && !am.canScheduleExactAlarms()) return; val now=Calendar.getInstance(); val t=Calendar.getInstance().apply{set(Calendar.HOUR_OF_DAY,8);set(Calendar.MINUTE,0);set(Calendar.SECOND,0);set(Calendar.MILLISECOND,0);if(!after(now))add(Calendar.DATE,1)}; val end=Calendar.getInstance().apply{set(2026,9,4,23,59,59)}; if(t.timeInMillis>end.timeInMillis)return; val pi=PendingIntent.getBroadcast(c,10,Intent(c,AlarmReceiver::class.java),PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE); am.setAlarmClock(AlarmManager.AlarmClockInfo(t.timeInMillis,pi),pi)}
    private fun Calendar.after(o:Calendar)=timeInMillis>o.timeInMillis
    fun openAlarmPermission(c:Context){if(Build.VERSION.SDK_INT>=31)c.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:${c.packageName}")))}
}
