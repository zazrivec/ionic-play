package controllers

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.{BSONDateTime, BSONHandler, Macros}
import scala.concurrent.Future
import reactivemongo.api.{QueryOpts, Cursor}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import org.slf4j.{LoggerFactory, Logger}
import play.api.mvc._
import play.api.libs.json._

/**
 * Created by zazrivj on 28.11.2014.
 */
class Services extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Services])

  def accounts: JSONCollection = db.collection[JSONCollection]("accounts")
  def owners: JSONCollection = db.collection[JSONCollection]("owners")
  def txs: JSONCollection = db.collection[JSONCollection]("txs")

  import models._
  import models.JsonFormats._


  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    val fmt = ISODateTimeFormat.dateTime()
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }

  def firstOwner() = Action.async {

    val futureOwner: Future[Option[JsValue]] = owners.find(Json.obj()).one[JsValue]

    futureOwner.map {
      case Some(owner) => Ok(owner)
      case None => NotFound(Json.obj("message" -> "No such item"))
    }

  }

  def owner(id: String) = Action.async {

    val futureOwner: Future[Option[JsValue]] = owners.find(Json.obj("_id" -> Json.obj("$oid" -> id))).one[JsValue]

    futureOwner.map {
      case Some(owner) => Ok(owner)
      case None => NotFound(Json.obj("message" -> "No such item"))
    }

  }

  def findAccounts(id : String) = Action.async {
    val cursor: Cursor[Account] = accounts.
      find(Json.obj("owner" -> Json.obj("$oid" -> id))).
      cursor[Account]

    val futureAccountsList: Future[List[Account]] = cursor.collect[List]()

    val futureAccountJsonArray: Future[JsArray] = futureAccountsList.map { accounts =>
      Json.arr(accounts)
    }

    futureAccountJsonArray.map { accounts =>
      Ok(accounts)
    }

  }

  def account(id : String) = Action.async {
    val futureAccount: Future[Option[JsValue]] = accounts.
      find(Json.obj("_id" -> Json.obj("$oid" -> id))).
      one[JsValue]

    futureAccount.map {
      case Some(account) => Ok(account)
      case None => NotFound(Json.obj("message" -> "No such item"))
    }

  }

  def findTx(accountId: String, bookBefore: Long, page: Long) = Action.async {
    val cursor: Cursor[Tx] = txs.
      find(
        Json.obj(
          "accountId" -> Json.obj("$oid" -> accountId)//,
          //"book" -> Json.obj("$lt" -> DateTime.now().minus(bookBefore))
        )).
      sort(Json.obj("book" -> 1)).
//      options(batchSizeN = 10)
      cursor[Tx]

    val futureTxList: Future[List[Tx]] = cursor.collect[List]()

    // transform the list into a JsArray
    val futureTxJsonArray: Future[JsArray] = futureTxList.map { tx =>
      Json.arr(tx)
    }
    // everything's ok! Let's reply with the array
    futureTxJsonArray.map { tx =>
      Ok(tx)
    }

  }

}
