package com.helloscala.akka.security.util

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 10:33:23
 */
class ScalaObjectMapper extends ObjectMapper with com.fasterxml.jackson.module.scala.ScalaObjectMapper {}
