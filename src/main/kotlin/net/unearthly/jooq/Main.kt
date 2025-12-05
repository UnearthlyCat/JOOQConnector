package net.unearthly.jooq

import cn.nukkit.plugin.PluginBase

class Main : PluginBase() {

    override fun onLoad() {
        this.setJOOQMessagesEnabled(false)
    }

    private fun setJOOQMessagesEnabled(enabled: Boolean) {
        val value = (!enabled).toString()
        System.setProperty("org.jooq.no-logo", value)
        System.setProperty("org.jooq.no-tips", value)
    }

}