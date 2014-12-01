package models

import org.joda.time.DateTime
import reactivemongo.bson.BSONObjectID

/**
 * Created by zazrivj on 28.11.2014.
 */

case class Tx(accountId: BSONObjectID, `type`: String, note:String, account: String, process: DateTime, book: DateTime, amount: Double)

case class Account(_id: Option[BSONObjectID] = None, owner: BSONObjectID, title: String, balance: Double, nbr: String, iban: String, description: Option[String] = None)

case class Owner(_id: Option[BSONObjectID] = None, name: String)

object JsonFormats {
  import play.api.libs.json.Json
  import play.api.data._
  import play.api.data.Forms._
  import play.modules.reactivemongo.json.BSONFormats._

  implicit val accountFormat = Json.format[Account]
  implicit val txFormat = Json.format[Tx]
  implicit val ownerFormat = Json.format[Owner]
}