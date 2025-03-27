package de.phyrone.bot

import org.graalvm.polyglot.*

fun main(args: Array<String>) {


    val context = Context.newBuilder("js", "python", "wasm")
        .sandbox(SandboxPolicy.CONSTRAINED)
        .allowHostAccess(HostAccess.ISOLATED)
        //.option("engine.SpawnIsolate", "true")
        .allowValueSharing(true)
        .allowCreateProcess(false)
        .allowCreateThread(false)

        .build()

    context.eval(Source.newBuilder("python","""
        print("Hello World")
    """.trimIndent(),"<internal>").cached(true).build())



}