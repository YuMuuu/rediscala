import model.Token

object RequestValidate {
  /**
    *leftをrequestvalidateerrorみたいな型で表現する
    */
  def validator(key:String, value:String): Either[Exception,Token] = {
    //Right(Token(key, value))
    key match {
      case it if it == ("+" | )=> Right(Token(key, value))
    }
  }

}
