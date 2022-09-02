package org.example.domain.values;

import co.com.sofka.domain.generic.Identity;

public class CartaMaestraId extends Identity {

    public CartaMaestraId() {
    }

    public CartaMaestraId(String uuid) {
        super(uuid);
    }

    public static CartaMaestraId of(String uuid) {  return new CartaMaestraId(uuid); }


}
