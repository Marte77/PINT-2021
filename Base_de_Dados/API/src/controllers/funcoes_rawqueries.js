const sequelize = require('../model/database');
async function atualizarRanking(){
    let querystring = 'do '+
    '$do$ '+
        'declare cursorranking cursor for '+
        'with cte_ranking as('+
            'select "PessoaIDPessoa" as idp,rank() over (ORDER by "Pontuacao" desc), "tipo" from '+
            '(select "PessoaIDPessoa","Pontos" as "Pontuacao",'+"'UI'"+' as "tipo" from "Utils_Instituicaos" '+
            'union select "PessoaIDPessoa","Pontos_Outro_Util" as "Pontuacao",'+"'IO' "+ 
            'as "tipo" from "Outros_Utils") as tabelaranking) '+
        'select * from cte_ranking;'+
        'begin '+
            //--open cursorranking;
            ' for linha in cursorranking loop '+
                ' if linha.tipo = '+"'IO'"+' then '+//--é outro util
                    ' update "Outros_Utils" set "Ranking" = linha.rank where "PessoaIDPessoa" = linha.idp; '+
                ' else '+
                    'update "Utils_Instituicaos" set "Ranking" = linha.rank where "PessoaIDPessoa" = linha.idp; '+
                'end if; '+
            'end loop; '+
            //--close cursorranking;
            //--deallocate cursorranking;
        'end '+
    '$do$; '
    
    await sequelize.query(querystring)
}
module.exports = atualizarRanking()