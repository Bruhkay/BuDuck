# okey

arkadaslar oncelikle..okey oynamayi ogretmeniz lazim @burkay

görev dağılımını ayarlayalım mı?

Bazı yerlere comment ekledim. Anlamadığım veya bana yanlış gelen yerlere. Buraya da listeleyeyim.
1.  Player class usefulTiles metodu: line 71 
    "if (playerTiles[i].getValue() - playerTiles[i - longestChain].getValue() == longestChain)"
    Burada "i - longestChain" < 0 olduğu durumlar oluyor sanki.
    Böyle olunca da array in indeix'i negatif oluyor.
    Onu düzeltmemiz lazım. Ama ben o metodu da çok anlamadım. 
    Anlayan biri el atarsa çok makbule geçer.





2.  Player class getAndRemoveTile() methodu: line 118
    Ben burda hala plusWhat ı anlamadım.
    Yani benim düşünceme göre plusWhat ı ekleyince fazladan 1 tane index atlıyoru.
    Buna da bi bakarsanız çok ii olur





3.  Simplified_Okey_Game class getPlayerWithHighestLongestChain() methodu: line 99
    Bu method winnerları doğru döndürmüyor olabilir. 
    Çünkü mesela 3 oyuncunun sırasıyla 5-5-7 uzunluğunda longChain leri olsun.
    Bu kodda ilk başta longChain 5 oluyor ve ilk oyuncuyu winner[0]'a yerleştiriyor. Sonra ikinci oyuncunun da 5 olduğu için o da winner[1]'e yerleşiyor.
    Üçüncü oyuncuya gelince hepsinden fazla zincire sahip olduğundan winner[0] güncelleniyor. Ama önceden koyduğu winner[2]'deki oyuncu hala orda kalmış oluyor.
    Bunu da düzeltmemiz lazım sanırım.
    Ben bunu çözmek i.in bir tane alternatif kod yazdım. Buraya ekleyim. (Ekstra bir tane methıd ile  yazdım)


    /**
      * Finds the players who have the longest chain
      * @return winners
      */
    public Player[] getPlayerWithHighestLongestChainTRY() 
    {
        int longestEver = this.totalLongestChain(players);
        Player[] winners = new Player[4];
        int currentWinner = 0;
        for(int i = 0; i < players.length; i++)
        {
            if(players[i].findLongestChain() == longestEver);
            {
                winners[currentWinner] = players[i];
                currentWinner++;
            }
        }
        return Arrays.copyOf(winners, winners.length);
    }


    /**
     * Compares all players' longest chain and return longest one
     * @param players all players in the game
     * @return longest chainn ever
     */
    public int totalLongestChain(Player[] players)
    {
        int longest = players[0].findLongestChain();
        for(int i = 1; i < players.length; i++)
        {
            if(players[i].findLongestChain() > longest)
            {
                longest = players[i].findLongestChain();
            }
        }
        return longest;
    }





4. Simplified_Okey_Game class distributeTilesToPlayers() methodu: line 45
    players[0]'a 15 diğerlerine 14 tile dağıtılmalı.





    