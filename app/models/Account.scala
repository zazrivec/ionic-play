package models

import org.joda.time.DateTime
import reactivemongo.bson.BSONObjectID

/**
 * Created by zazrivj on 28.11.2014.
 */

case class Tx(accountId: BSONObjectID, `type`: String, note:String, account: String, process: DateTime, book: DateTime, amount: Double)

case class Account(_id: BSONObjectID, owner: BSONObjectID, title: String, balance: Double, nbr: String, iban: String, description: String)

case class Owner(_id: BSONObjectID, name: String)

object JsonFormats {
  import play.api.libs.json.Json
  import play.api.data._
  import play.api.data.Forms._

  implicit val accountFormat = Json.format[Account]
  implicit val txFormat = Json.format[Tx]
}