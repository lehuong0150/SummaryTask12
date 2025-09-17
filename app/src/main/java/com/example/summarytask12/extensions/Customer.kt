package com.example.summarytask12.extensions

import com.example.summarytask12.model.users.Customer
import com.example.summarytask12.utils.MemberShipLevels

fun Customer.isVIP(): Boolean = getMembershipLevel == MemberShipLevels.VIP

fun Customer.canBookAdvance(days: Int): Boolean = when (getMembershipLevel) {
    MemberShipLevels.VIP -> days > 60
    MemberShipLevels.PREMIUM -> days in 30..60
    MemberShipLevels.BASIC -> days in 15..29
    else -> days >=1
}


