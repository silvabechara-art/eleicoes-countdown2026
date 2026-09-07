package br.com.eleicoes.contagem
import android.content.*
class BootReceiver: BroadcastReceiver(){override fun onReceive(c:Context,i:Intent?){AlarmScheduler.schedule(c)}}
