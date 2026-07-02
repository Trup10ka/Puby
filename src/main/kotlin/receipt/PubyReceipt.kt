package me.trup10ka.puby.receipt

import me.trup10ka.puby.data.PubyEventMember

class PubyReceipt(
    val receiptId: Int,
    val payers: MutableList<PubyEventMember> = mutableListOf(),
    val payersAccounts: MutableMap<PubyEventMember, String> = mutableMapOf(),
    val itemsPurchased: MutableList<ReceiptItem> = mutableListOf()
)
{

}
