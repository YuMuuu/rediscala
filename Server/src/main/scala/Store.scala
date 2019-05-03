import com.sun.tools.corba.se.idl.StringGen
import com.sun.tools.internal.xjc.ErrorReceiver


trait Key

trait Value

trait Store[key <: Key, value <: Value]








//def set(key: Key, value: Value, data: Map[Key, Value]): Map[Key, Value] = {
//  data + (key -> value)
//}
//
//def setNX(key: Key, value: Value, data: Map[Key, Value]): Unit ={
//  if(data.contains(key)){
//    set(key, value, data)
//  }
//}
//
//def setXX(key: Key, value: Value, data: Map[Key, Value]): Unit ={
//  if(!data.contains(key)){
//    set(key, value, data)
//  }
//}
//
//def get(key: Key, data: Map[Key, Value])
//
//def incrBy(key: Key, value: Value, data: Map[Key, Value])
//
//def decrBy(key: Key, value: Value, data: Map[Key, Value])
//
//def delete(key: List[Key], data: Map[Key, Value])
//
//def exists(key: List[Key], data: Map[Key, Value])

