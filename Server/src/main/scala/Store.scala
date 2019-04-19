class Store {
  var data: Map[Any, Any] = Map()

  def set(key: Any, value: Any): String = {
    println("SET")
    data = data + (key -> value)
    println(data.getOrElse(key, None))
    "OK"
  }

  def setNX(key: Any, value: Any): Any = {
    if (data.contains(key)) {
      0
    } else {
      set(key, value)
    }
  }

  def setXX(key: Any, value: Any): Any = {
    if (data.contains(key)) {
      set(key, value)
    } else {
      0
    }
  }

  def get(key: Any): Any = {
    data.getOrElse(key, "-") match {
      case "-" => List("-", "Data not found")
      case i: Any => i
    }
  }

  def incrBy(key: Any, value: Long):Any = {
    val preVal = get(key)
    preVal match {
      case i: List[String] => i
      case i: Long => {data = data + (key -> (i + value)); i + value }
      case i: String => {data = data + (key -> (i.toLong + value)); i.toLong + value }
      case _ => List("-", "type mismatch")
    }
  }

  def decrBy(key:Any, value:Long):Any = {
    val preVal = get(key)
    preVal match {
      case i: List[String] => i
      case i: Long => {data = data + (key -> (i - value)); i - value }
      case i: String => {data = data + (key -> (i.toLong - value)); i.toLong - value }
      case _ => List("-", "type mismatch")
    }
  }

  def delete(keys: List[Any]): Int = {
    val preSize = data.size
    data = data -- keys
    preSize - data.size
  }

  def exists(keys: List[Any]): Int = {
    data.size - (data -- keys).size
  }
}


object Store {
  def apply = new Store
  def set(key: Any, value: Any):String = apply.set(key, value)
  def setNX(key: Any, value: Any) = apply.setNX(key, value)
  def setXX(key: Any, value: Any) = apply.setXX(key, value)
  def get(key: Any) = apply.get(key)
  def incrBy(key: Any, value: Long) = apply.incrBy(key, value)
  def decrBy(key:Any, value:Long) = apply.decrBy(key, value)
  def delete(keys: List[Any]):Int = delete(keys)
  def exists(keys: List[Any]):Int = exists(keys)
}
