package com.helloscala.akka.oauth.util

import akka.http.scaladsl.model.{ ContentTypes, FormData, HttpRequest }
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer

import scala.concurrent.Future

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 11:16:03
 */
object HttpUtils {
  def getParameterValues(request: HttpRequest, name: String)(implicit ec: Materializer): Future[List[String]] = {
    import ec.executionContext
    val query = request.uri.query()
    query.getAll(name) match {
      case Nil if request.entity.contentType == ContentTypes.`application/x-www-form-urlencoded` =>
        Unmarshal(request.entity).to[FormData].map(form => form.fields.getAll(name))
      case values => Future.successful(values)
    }
  }

}
