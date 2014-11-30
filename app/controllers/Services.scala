package controllers

import org.joda.time.DateTime
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}
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

  def owner() = Action.async {

    val futureOwner: Future[Option[Owner]] = owners.find(Json.obj()).one[Owner]

    val futureOwnerJson: Future[JsObject] = futureOwner.map { owner => owner.map { Json.obj(_) } }

    futureOwnerJson.map {
      owner =>
        Ok(owner)
    }

  }

  def findAccounts(id : String) = Action.async {
    val cursor: Cursor[Account] = accounts.
      find(Json.obj("owner" -> Json.obj("$oid" -> id))).
      cursor[Account]

    val futureAccountsList: Future[List[Account]] = cursor.collect[List]()

    val futureAccountJsonArray: Future[JsArray] = futureAccountsList.map { account => Json.arr(account) }
    futureAccountJsonArray.map {
      accounts =>
        Ok(accounts(0))
    }
  }

  def findTx(accountId: String, bookBefore: Long, page: Long) = Action.async {
    val cursor: Cursor[Tx] = txs.
      find(Json.obj("accountId" -> Json.obj("$oid" -> accountId), "book" -> Json.obj("$lt" -> bookBefore))).
      sort(Json.obj("book" -> 1)).
//      options(batchSizeN = 10)
      cursor[Tx]

    val futureTxList: Future[List[Tx]] = cursor.collect[List]()

    // transform the list into a JsArray
    val futureTxJsonArray: Future[JsArray] = futureTxList.map { txes =>
      Json.arr(txes)
    }
    // everything's ok! Let's reply with the array
    futureTxJsonArray.map {
      tx =>
        Ok(tx(0))
    }
  }

}
